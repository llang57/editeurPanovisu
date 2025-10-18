/**
 * Kernel OpenCL pour redimensionnement d'image avec interpolation Lanczos3
 * 
 * L'interpolation Lanczos offre la meilleure qualité pour l'agrandissement d'images,
 * en préservant particulièrement bien les hautes fréquences et les détails fins.
 * 
 * Utilise une fenêtre de 6x6 pixels (a=3) avec la fonction sinc comme base.
 * Peut produire de légers artefacts de "ringing" sur les contours à fort contraste.
 * 
 * @author PanoVisu Team
 * @version 1.0
 */

#ifndef M_PI
#define M_PI 3.14159265358979323846f
#endif

/**
 * Fonction sinc normalisée : sin(π·x) / (π·x)
 * Cas spécial : sinc(0) = 1
 * 
 * @param x Valeur d'entrée
 * @return Valeur sinc
 */
float sinc(float x) {
    if (fabs(x) < 0.0001f) {
        return 1.0f;
    }
    float px = M_PI * x;
    return sin(px) / px;
}

/**
 * Fonction de Lanczos avec fenêtre de taille a
 * L(x) = sinc(x) · sinc(x/a) pour |x| < a, 0 sinon
 * 
 * @param x Distance au pixel
 * @param a Taille de la fenêtre (typiquement 3)
 * @return Poids Lanczos
 */
float lanczos(float x, float a) {
    float ax = fabs(x);
    
    // En dehors de la fenêtre : pas de contribution
    if (ax >= a) {
        return 0.0f;
    }
    
    // Au centre : valeur maximale
    if (ax < 0.0001f) {
        return 1.0f;
    }
    
    // Calcul Lanczos : sinc(x) · sinc(x/a)
    return sinc(x) * sinc(x / a);
}

/**
 * Kernel principal de redimensionnement Lanczos3
 * Utilise une fenêtre 6x6 (a=3) pour un échantillonnage de haute qualité
 * Chaque thread calcule un pixel de l'image de sortie
 * 
 * @param input     Image source (RGBA, uchar4)
 * @param output    Image destination (RGBA, uchar4)
 * @param srcWidth  Largeur de l'image source
 * @param srcHeight Hauteur de l'image source
 * @param dstWidth  Largeur de l'image destination
 * @param dstHeight Hauteur de l'image destination
 */
__kernel void resize_lanczos3(
    __global const uchar4* input,
    __global uchar4* output,
    int srcWidth,
    int srcHeight,
    int dstWidth,
    int dstHeight
) {
    // Coordonnées du pixel de sortie
    int x = get_global_id(0);
    int y = get_global_id(1);
    
    // Vérification des limites
    if (x >= dstWidth || y >= dstHeight) {
        return;
    }
    
    // Paramètre de Lanczos (taille de fenêtre)
    const float a = 3.0f;
    
    // Calcul de la position correspondante dans l'image source
    float srcX = (x + 0.5f) * (float)srcWidth / (float)dstWidth - 0.5f;
    float srcY = (y + 0.5f) * (float)srcHeight / (float)dstHeight - 0.5f;
    
    // Centre du voisinage 6x6
    int x0 = (int)floor(srcX);
    int y0 = (int)floor(srcY);
    
    // Distances fractionnaires
    float dx = srcX - (float)x0;
    float dy = srcY - (float)y0;
    
    // Accumulateurs pour la couleur et le poids total
    float4 color = (float4)(0.0f, 0.0f, 0.0f, 0.0f);
    float totalWeight = 0.0f;
    
    // Échantillonnage du voisinage 6x6 (de -2 à +3 relatif à x0,y0)
    // Pour Lanczos3, on échantillonne de -a à +a, soit -3 à +3
    // Mais pour optimiser, on va de -2 à +3 (6 pixels)
    for (int j = -2; j <= 3; j++) {
        for (int i = -2; i <= 3; i++) {
            // Coordonnées du pixel source avec clamping
            int xi = clamp(x0 + i, 0, srcWidth - 1);
            int yi = clamp(y0 + j, 0, srcHeight - 1);
            
            // Lecture du pixel source
            uchar4 pixel = input[yi * srcWidth + xi];
            
            // Calcul des poids Lanczos en x et y
            float wx = lanczos((float)i - dx, a);
            float wy = lanczos((float)j - dy, a);
            float weight = wx * wy;
            
            // Accumulation pondérée
            color.x += (float)pixel.x * weight;
            color.y += (float)pixel.y * weight;
            color.z += (float)pixel.z * weight;
            color.w += (float)pixel.w * weight;
            
            totalWeight += weight;
        }
    }
    
    // Normalisation par le poids total (important pour Lanczos)
    if (totalWeight > 0.0001f) {
        color.x /= totalWeight;
        color.y /= totalWeight;
        color.z /= totalWeight;
        color.w /= totalWeight;
    }
    
    // Clamping et conversion en uchar4
    output[y * dstWidth + x] = (uchar4)(
        clamp((int)(color.x + 0.5f), 0, 255),
        clamp((int)(color.y + 0.5f), 0, 255),
        clamp((int)(color.z + 0.5f), 0, 255),
        clamp((int)(color.w + 0.5f), 0, 255)
    );
}

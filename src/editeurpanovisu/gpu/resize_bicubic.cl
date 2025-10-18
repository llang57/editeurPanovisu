/**
 * Kernel OpenCL pour redimensionnement d'image avec interpolation bicubique (Catmull-Rom)
 * 
 * L'interpolation bicubique utilise un voisinage 4x4 pixels et offre un excellent
 * compromis entre qualité et performance. Elle préserve mieux les détails et les
 * contours que l'interpolation bilinéaire.
 * 
 * Paramètre a = -0.5 (Catmull-Rom) pour un bon équilibre netteté/lissage
 * 
 * @author PanoVisu Team
 * @version 1.0
 */

/**
 * Fonction d'interpolation cubique 1D (Catmull-Rom)
 * 
 * @param v Distance normalisée au pixel (0 à 2)
 * @return Poids de contribution du pixel
 */
float cubic(float v) {
    const float a = -0.5f; // Paramètre Catmull-Rom (valeur standard)
    float av = fabs(v);
    
    if (av < 1.0f) {
        // Région proche (0 à 1) : interpolation cubique principale
        return (a + 2.0f) * av*av*av - (a + 3.0f) * av*av + 1.0f;
    } else if (av < 2.0f) {
        // Région éloignée (1 à 2) : décroissance cubique
        return a * av*av*av - 5.0f * a * av*av + 8.0f * a * av - 4.0f * a;
    }
    // Au-delà de 2 pixels : pas de contribution
    return 0.0f;
}

/**
 * Kernel principal de redimensionnement bicubique
 * Chaque thread calcule un pixel de l'image de sortie
 * 
 * @param input     Image source (RGBA, uchar4)
 * @param output    Image destination (RGBA, uchar4)
 * @param srcWidth  Largeur de l'image source
 * @param srcHeight Hauteur de l'image source
 * @param dstWidth  Largeur de l'image destination
 * @param dstHeight Hauteur de l'image destination
 */
__kernel void resize_bicubic(
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
    
    // Calcul de la position correspondante dans l'image source
    // +0.5 pour centrer le pixel, -0.5 pour ajuster le mapping
    float srcX = (x + 0.5f) * (float)srcWidth / (float)dstWidth - 0.5f;
    float srcY = (y + 0.5f) * (float)srcHeight / (float)dstHeight - 0.5f;
    
    // Coin supérieur gauche du voisinage 4x4
    int x0 = (int)floor(srcX);
    int y0 = (int)floor(srcY);
    
    // Distances fractionnaires (0 à 1)
    float dx = srcX - (float)x0;
    float dy = srcY - (float)y0;
    
    // Accumulateur pour la couleur interpolée (float pour précision)
    float4 color = (float4)(0.0f, 0.0f, 0.0f, 0.0f);
    
    // Échantillonnage du voisinage 4x4 (de -1 à +2 relatif à x0,y0)
    for (int j = -1; j <= 2; j++) {
        for (int i = -1; i <= 2; i++) {
            // Coordonnées du pixel source avec clamping aux bords
            int xi = clamp(x0 + i, 0, srcWidth - 1);
            int yi = clamp(y0 + j, 0, srcHeight - 1);
            
            // Lecture du pixel source
            uchar4 pixel = input[yi * srcWidth + xi];
            
            // Calcul des poids bicubiques en x et y
            float wx = cubic((float)i - dx);
            float wy = cubic((float)j - dy);
            float weight = wx * wy;
            
            // Accumulation pondérée des composantes RGBA
            color.x += (float)pixel.x * weight; // Red
            color.y += (float)pixel.y * weight; // Green
            color.z += (float)pixel.z * weight; // Blue
            color.w += (float)pixel.w * weight; // Alpha
        }
    }
    
    // Clamping des valeurs dans [0, 255] et conversion en uchar4
    output[y * dstWidth + x] = (uchar4)(
        clamp((int)(color.x + 0.5f), 0, 255),
        clamp((int)(color.y + 0.5f), 0, 255),
        clamp((int)(color.z + 0.5f), 0, 255),
        clamp((int)(color.w + 0.5f), 0, 255)
    );
}

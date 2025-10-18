/**
 * Kernel OpenCL pour la transformation Cube vers Équirectangulaire
 * Optimisé pour exécution parallèle sur GPU
 * 
 * @param cubeFaces     Tableau des 6 faces du cube (RGBA, 4 bytes par pixel)
 * @param equi          Image équirectangulaire destination (RGBA, 4 bytes par pixel)
 * @param tailleCube    Taille d'une face du cube
 * @param tailleEqui    Largeur de l'image équirectangulaire
 * @param hauteurEqui   Hauteur de l'image équirectangulaire
 */
__kernel void cube2equi(
    __global const uchar4* cubeFaces,  // 6 faces concaténées
    __global uchar4* equi,
    int tailleCube,
    int tailleEqui,
    int hauteurEqui
) {
    // Coordonnées du pixel dans l'image équirectangulaire
    int pX = get_global_id(0);
    int pY = get_global_id(1);
    
    // Vérifier les limites
    if (pX >= tailleEqui || pY >= hauteurEqui) {
        return;
    }
    
    // Constantes
    float PI = 3.14159265359f;
    float TWO_PI = 2.0f * PI;
    
    // Calculer les angles sphériques
    float theta = ((float)pX / (float)tailleEqui) * TWO_PI - PI;
    float phi = ((float)pY / (float)hauteurEqui) * PI;
    
    // Convertir en coordonnées cartésiennes
    float sinPhi = sin(phi);
    float cosPhi = cos(phi);
    float sinTheta = sin(theta);
    float cosTheta = cos(theta);
    
    float x = sinPhi * sinTheta;
    float y = cosPhi;
    float z = sinPhi * cosTheta;
    
    // Déterminer quelle face du cube utiliser
    float absX = fabs(x);
    float absY = fabs(y);
    float absZ = fabs(z);
    
    int face;
    float u, v;
    
    // Trouver la face dominante et calculer les coordonnées UV
    if (absX >= absY && absX >= absZ) {
        // Left (x < 0) ou Right (x > 0)
        if (x > 0) {
            // Right face (2)
            face = 2;
            u = -z / x;
            v = -y / x;
        } else {
            // Left face (3)
            face = 3;
            u = z / -x;
            v = -y / -x;
        }
    } else if (absY >= absX && absY >= absZ) {
        // Top (y > 0) ou Bottom (y < 0)
        if (y > 0) {
            // Bottom face (5)
            face = 5;
            u = x / y;
            v = z / y;
        } else {
            // Top face (4)
            face = 4;
            u = x / -y;
            v = -z / -y;
        }
    } else {
        // Front (z > 0) ou Behind (z < 0)
        if (z > 0) {
            // Front face (0)
            face = 0;
            u = x / z;
            v = -y / z;
        } else {
            // Behind face (1)
            face = 1;
            u = -x / -z;
            v = -y / -z;
        }
    }
    
    // Convertir u,v de [-1,1] vers [0, tailleCube]
    float faceX = (u + 1.0f) * 0.5f * (float)(tailleCube - 1);
    float faceY = (v + 1.0f) * 0.5f * (float)(tailleCube - 1);
    
    // Interpolation bilinéaire
    int x0 = (int)floor(faceX);
    int y0 = (int)floor(faceY);
    int x1 = min(x0 + 1, tailleCube - 1);
    int y1 = min(y0 + 1, tailleCube - 1);
    
    float fx = faceX - x0;
    float fy = faceY - y0;
    
    // Calculer l'offset de la face dans le buffer
    int faceOffset = face * tailleCube * tailleCube;
    
    // Récupérer les 4 pixels voisins
    uchar4 c00 = cubeFaces[faceOffset + y0 * tailleCube + x0];
    uchar4 c10 = cubeFaces[faceOffset + y0 * tailleCube + x1];
    uchar4 c01 = cubeFaces[faceOffset + y1 * tailleCube + x0];
    uchar4 c11 = cubeFaces[faceOffset + y1 * tailleCube + x1];
    
    // Interpolation bilinéaire pour chaque composante
    float red = (1.0f - fx) * (1.0f - fy) * c00.x +
                fx * (1.0f - fy) * c10.x +
                (1.0f - fx) * fy * c01.x +
                fx * fy * c11.x;
              
    float green = (1.0f - fx) * (1.0f - fy) * c00.y +
                  fx * (1.0f - fy) * c10.y +
                  (1.0f - fx) * fy * c01.y +
                  fx * fy * c11.y;
              
    float blue = (1.0f - fx) * (1.0f - fy) * c00.z +
                 fx * (1.0f - fy) * c10.z +
                 (1.0f - fx) * fy * c01.z +
                 fx * fy * c11.z;
              
    float alpha = (1.0f - fx) * (1.0f - fy) * c00.w +
                  fx * (1.0f - fy) * c10.w +
                  (1.0f - fx) * fy * c01.w +
                  fx * fy * c11.w;
    
    // Écrire le pixel dans l'image équirectangulaire
    int idx = pY * tailleEqui + pX;
    equi[idx] = (uchar4)((uchar)red, (uchar)green, (uchar)blue, (uchar)alpha);
}

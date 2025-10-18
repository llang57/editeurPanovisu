/**
 * Kernel OpenCL pour la transformation Équirectangulaire vers Cube
 * Optimisé pour exécution parallèle sur GPU
 * 
 * @param equi      Image équirectangulaire source (RGBA, 4 bytes par pixel)
 * @param cube      Image cube destination (RGBA, 4 bytes par pixel)
 * @param tailleEqui    Largeur de l'image équirectangulaire
 * @param hauteurEqui   Hauteur de l'image équirectangulaire
 * @param tailleCube    Taille d'une face du cube
 * @param face          Index de la face (0=Front, 1=Behind, 2=Right, 3=Left, 4=Top, 5=Bottom)
 */
__kernel void equi2cube_face(
    __global const uchar4* equi,
    __global uchar4* cube,
    int tailleEqui,
    int hauteurEqui,
    int tailleCube,
    int face
) {
    // Coordonnées du pixel dans la face du cube
    int pX = get_global_id(0);
    int pY = get_global_id(1);
    
    // Vérifier les limites
    if (pX >= tailleCube || pY >= tailleCube) {
        return;
    }
    
    // Normaliser les coordonnées [-1, 1]
    float X = (2.0f * (float)pX - (float)tailleCube) / (float)tailleCube;
    float Y = (2.0f * (float)pY - (float)tailleCube) / (float)tailleCube;
    float Z;
    
    // Vecteur 3D selon la face
    float vecX, vecY, vecZ;
    
    if (face == 0) {
        // Front
        vecX = X;
        vecY = -Y;
        vecZ = 1.0f;
    } else if (face == 1) {
        // Behind
        vecX = -X;
        vecY = -Y;
        vecZ = -1.0f;
    } else if (face == 2) {
        // Right
        vecX = 1.0f;
        vecY = -Y;
        vecZ = -X;
    } else if (face == 3) {
        // Left
        vecX = -1.0f;
        vecY = -Y;
        vecZ = X;
    } else if (face == 4) {
        // Top
        vecX = X;
        vecY = -1.0f;
        vecZ = -Y;
    } else {
        // Bottom
        vecX = X;
        vecY = 1.0f;
        vecZ = Y;
    }
    
    // Calcul des angles sphériques
    float theta = atan2(vecX, vecZ);
    float r = sqrt(vecX * vecX + vecY * vecY + vecZ * vecZ);
    float phi = acos(vecY / r);
    
    // Normaliser theta et phi
    float PI = 3.14159265359f;
    float TWO_PI = 2.0f * PI;
    
    // Mapper vers les coordonnées de l'image équirectangulaire
    float rapport = (float)tailleEqui / TWO_PI;
    float pixelX = (theta + PI) * rapport;
    float pixelY = phi * rapport;
    
    // Interpolation bilinéaire
    int x0 = (int)floor(pixelX);
    int y0 = (int)floor(pixelY);
    int x1 = x0 + 1;
    int y1 = y0 + 1;
    
    float fx = pixelX - x0;
    float fy = pixelY - y0;
    
    // Gérer les bords avec wrapping horizontal
    x0 = (x0 + tailleEqui) % tailleEqui;
    x1 = (x1 + tailleEqui) % tailleEqui;
    y0 = clamp(y0, 0, hauteurEqui - 1);
    y1 = clamp(y1, 0, hauteurEqui - 1);
    
    // Récupérer les 4 pixels voisins
    uchar4 c00 = equi[y0 * tailleEqui + x0];
    uchar4 c10 = equi[y0 * tailleEqui + x1];
    uchar4 c01 = equi[y1 * tailleEqui + x0];
    uchar4 c11 = equi[y1 * tailleEqui + x1];
    
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
    
    // Écrire le pixel dans la face du cube
    int idx = pY * tailleCube + pX;
    cube[idx] = (uchar4)((uchar)red, (uchar)green, (uchar)blue, (uchar)alpha);
}

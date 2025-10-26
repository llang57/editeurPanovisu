/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editeurpanovisu;

import java.io.File;

/**
 *
 * @author LANG Laurent
 */
public class ImageEditeurHTML {
        
        private String strImagePath="";

        /**
     * Retourne la valeur de strImage.
     *
         * @return the strImage
         */
        public String getStrNomImage() {            
            return strImagePath.substring(strImagePath.lastIndexOf(File.separator) + 1, strImagePath.length());
        }


        /**
     * Retourne la valeur de strImagePath.
     *
         * @return the strImagePath
         */
        public String getStrImagePath() {
            return strImagePath;
        }

        /**
     * Définit la valeur de strImagePath.
     *
         * @param strImagePath the strImagePath to set
         */
        public void setStrImagePath(String strImagePath) {
            this.strImagePath = strImagePath;
        }

    }
    


package editeurpanovisu;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import javafx.scene.image.Image;

    public class CellulePanoramique {
        private Image imgPanoramique;
        private String strTitrePanoramique;
        private String strTitrePanoramiqueLigne2;
        private int iNumPano;

        /**
         * @return
         */
        public Image getImgPanoramique() {
            return imgPanoramique;
        }

        /**
         * @param imgPanoramique
         */
        public void setImgPanoramique(Image imgPanoramique) {
            this.imgPanoramique = imgPanoramique;
        }

        /**
         * @return
         */
        public String getStrTitrePanoramique() {
            return strTitrePanoramique;
        }

        /**
         * @param strTitre1
         */
        public void setStrTitrePanoramique(String strTitre1) {
            this.strTitrePanoramique = strTitre1;
        }

        /**
         * @return
         */
        public String getStrTitrePanoramiqueLigne2() {
            return strTitrePanoramiqueLigne2;
        }

        /**
         * @param strTitrePanoramiqueLigne2
         */
        public void setStrTitrePanoramiqueLigne2(String strTitrePanoramiqueLigne2) {
            this.strTitrePanoramiqueLigne2 = strTitrePanoramiqueLigne2;
        }

    /**
     * @return the iNumPano
     */
    public int getiNumPano() {
        return iNumPano;
    }

    /**
     * @param iNumPano the iNumPano to set
     */
    public void setiNumPano(int iNumPano) {
        this.iNumPano = iNumPano;
    }

    }


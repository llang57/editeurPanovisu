package editeurpanovisu;


import java.io.File;
import java.io.FileFilter;
/**
 * Génération de listes FileFilter
 * 
 * @author llang
 */
public class ExtensionsFilter implements FileFilter 
{
    private char[][] chExtensions;
    
    /**
     * Crée un filtre de fichiers basé sur les extensions
     * 
     * <p>Permet de filtrer les fichiers selon leurs extensions
     * (ex: ".jpg", ".png", ".xml"). La comparaison est insensible à la casse.</p>
     * 
     * @param strExtensions Tableau d'extensions à accepter (avec ou sans point)
     */
    ExtensionsFilter(String[] strExtensions)
    {
        int length = strExtensions.length;
        this.chExtensions = new char[length][];
        for (String s : strExtensions)
        {
            this.chExtensions[--length] = s.toCharArray();
        }
    }
    
    /**
     * Teste si un fichier correspond à l'une des extensions du filtre
     * 
     * <p>La comparaison est insensible à la casse (jpg = JPG = Jpg).</p>
     * 
     * @param fileCharge Fichier à tester
     * @return true si l'extension du fichier correspond à l'une des extensions du filtre
     */
    @Override
    public boolean accept(File fileCharge)
    {
        char[] chPath = fileCharge.getPath().toCharArray();
        for (char[] chExtension : chExtensions)
        {
            if (chExtension.length > chPath.length)
            {
                continue;
            }
            int iPStart = chPath.length - 1;
            int iEStart = chExtension.length - 1;
            boolean bSucces = true;
            for (int i = 0; i <= iEStart; i++)
            {
                if ((chPath[iPStart - i] | 0x20) != (chExtension[iEStart - i] | 0x20))
                {
                    bSucces = false;
                    break;
                }
            }
            if (bSucces)
                return true;
        }
        return false;
    }
}

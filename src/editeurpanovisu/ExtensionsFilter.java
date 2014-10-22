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
 * 
 * @param strExtensions 
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
 * 
 * @param fileCharge
 * @return boolean
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

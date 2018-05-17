package application;

import org.neuroph.core.data.DataSetRow;

//klasa przechowująca dane
public class Dane
{
	//zmienna przechowująca liczbę kolumn, jakie zajmuje jeden obrazek
    public final static int kolumny = 5;
    
    //zmienna przechowująca liczbę wierszy, jakie zajmuje jeden obrazek
    public final static int wiersze = 7;

    //zmienna przechowywująca znaki, tworzące obrazki cyfr
    public static String[][] obrazki =
    {
           {" III ",
            "I   I",
            "I   I",
            "I   I",
            "I   I",
            "I   I",
            " III "},
           {"  I  ",
            " II  ",
            "I I  ",
            "  I  ",
            "  I  ",
            "  I  ",
            "  I  "},
           {" III ",
            "I   I",
            "    I",
            "   I ",
            "  I  ",
            " I   ",
            "IIIII"},
           {" III ",
            "I   I",
            "    I",
            " III ",
            "    I",
            "I   I",
            " III "},
           {"   I ",
            "  II ",
            " I I ",
            "I  I ",
            "IIIII",
            "   I ",
            "   I "},
           {"IIIII",
            "I    ",
            "I    ",
            "IIII ",
            "    I",
            "I   I",
            " III "},
           {" III ",
            "I   I",
            "I    ",
            "IIII ",
            "I   I",
            "I   I",
            " III "},
           {"IIIII",
            "    I",
            "    I",
            "   I ",
            "  I  ",
            " I   ",
            "I    "},
           {" III ",
            "I   I",
            "I   I",
            " III ",
            "I   I",
            "I   I",
            " III "},
           {" III ",
            "I   I",
            "I   I",
            " IIII",
            "    I",
            "I   I",
            " III "}
    };
    
    //metoda przekształcająca obrazki na cyfry
    public static DataSetRow zObrazkaNaCyfre(String[] obrazek)
    {
    	//stworzenie macierzy składającej się z wierszy i kolumn obrazka
        DataSetRow dsr=new DataSetRow(Dane.wiersze * Dane.kolumny);

        double[] macierz=new double[Dane.kolumny * Dane.wiersze];

        for (int wiersz=0; wiersz<Dane.wiersze; wiersz++)
        {
            for (int kolumna=0; kolumna<Dane.kolumny; kolumna++)
            {
                int index = (wiersz * Dane.kolumny) + kolumna;
                char c = obrazek[wiersz].charAt(kolumna);
                macierz[index] = (c == 'I' ? 1 : -1);
            }
        }
        dsr.setInput(macierz);
        return dsr;
    }

    //metoda przekształcająca cyfry na obrazki
    public static String[] zCyfryNaObrazek(double[] cyfra)
    {
    	//zmienna przechowująca obrazki
        String[] obrazek = new String[cyfra.length / Dane.kolumny];
        
        //zmienna przechowująca "wiersz" obrazka
        String wiersz = "";

        for (int i=0; i<cyfra.length; i++)
        {
        	//jeśli cyfra w danym miejscu "ma piksel" to do wiersza dodawane jest 0, w przeciwnym wypadku pole jest puste
            if (cyfra[i]==1)
                wiersz+="I";
            else
                wiersz+=" ";
            
            //jeśli wielkość wiersza jest podzielna przez 5 i nie jest równa 0,
            //to obrazek[i/5] przypisuje się wartość wiersza, a następnie wiersz jest czyszczony
            if (wiersz.length() % 5==0 && wiersz.length()!=0)
            {
                obrazek[i / 5] = wiersz;
                wiersz = "";
            }
        }
        return obrazek;
    }
}

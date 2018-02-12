import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Version1 {

    private static ArrayList<String> result=new ArrayList<>();
    private static ArrayList<String> keys=new ArrayList<>();

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Введите ключ");
        String key = sc.nextLine();
        while (!key.equals("")){

            keys.add(key.toLowerCase());
            key=sc.nextLine();
        }
        System.out.println("Введите Text");
        String text = sc.nextLine();
        while (!text.equals("")) {
            check(text.toLowerCase());
            text = sc.nextLine();

        }
        for (int i=0;i<result.size();i++) {
            System.out.println(result.get(i));

        }
    }

    public static void check(String text) {
        boolean b = true;
        String[] words = text.split("\\s");

            for (int i = 0; i < keys.size(); i++) {
                String[] key = keys.get(i).split("\\s");
                if (b){
                for (int k = 0; k < key.length; k++) {
                    Pattern p = Pattern.compile("^" + key[k] + ";?" + "$");
                    for (int j = 0; j < words.length; j++) {
                        Matcher m = p.matcher(words[j]);
                        if (m.matches()) {
                            result.add(text);
                            b=false;
                            break;
                        }

                    }
                }
            }}

    }

}



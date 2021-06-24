package com.company;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    //1
    public static String hiddenAnagram(String str, String anagram) {
        str = str.toLowerCase().replaceAll("[^a-zA-Z]", "");
        anagram = anagram.toLowerCase().replaceAll("[^a-zA-Z]", "");
        String copy = anagram;
        StringBuilder res = new StringBuilder();

        char[] strArr = str.toCharArray();
        int t=0;

        for (int i =0;;) {

            if (copy.contains(String.valueOf(strArr[i]))){
                res.append(strArr[i]);
                copy = copy.replaceFirst(String.valueOf(strArr[i]),"");
                i++;
            }
            else {
                copy=anagram;
                res = new StringBuilder();
                t++;
                i=t;
                if (strArr.length - t < anagram.length() ) break;
            }
            if (copy.equals("")) return res.toString();

        }
        return "notfound";
    }

    //2
    public static List<String> collect(String str, int n) {
        if (str.length() < n) return Collections.emptyList();
        else {
            List<String> res = Stream.concat(List.of(str.substring(0,n)).stream(),collect(str.substring(n),n).stream()).collect(Collectors.toList());
            Collections.sort(res);
            return res;
        }
    }

    //3
    public static String NicoCipher(String message, String key) {
        key = key.toLowerCase();
        int[] keyArr = new int[key.length()];

        String [] keyStrArr = new String[key.length()];
        for (int i=0; i < key.length(); i++) keyStrArr[i] = key.substring(i, i+1);
        Arrays.sort(keyStrArr); // astr from sart - 1234 to 2134
        String keyStr = "";
        for (int i=0; i < keyStrArr.length; i++) keyStr += keyStrArr[i];
        for (int i=0; i < key.length(); i++) {
            String s = key.substring(i, i+1);
            int ind = keyStr.indexOf(key.substring(i, i+1));
            keyStr = keyStr.replaceFirst(s, " ");
            keyArr[ind] = i;
        }

        Map<Integer, String> encodeMessage = new HashMap<Integer, String>();
        int steps = 0;
        if (message.length() % key.length() == 0) steps = message.length() / key.length();
        else steps= message.length() / key.length() + 1;
        steps *= key.length();

        for (int i=0; i <steps; i++) {
            String letter = " ";
            if (i < message.length()) letter = message.substring(i, i+1);
            int index = i % keyArr.length;
            if (encodeMessage.get(index) != null) encodeMessage.put(index, encodeMessage.get(index) + letter);
            else encodeMessage.put(index, letter);
        }
        String ans = "";
        steps /=  key.length();
        for (int j=0; j < steps; j++) {
            for (int i=0; i < keyArr.length; i++) {
                String symbol = encodeMessage.get(keyArr[i]);
                ans += symbol.substring(j, j+1);
            }
        }
        return ans;
    }

    //4

    public static String TwoProduct(int[] array, int n) {
        int first=0, second=0;
        int[] answer = new int[2];
        for(int i=array.length-1;i>0;--i) {
            second=array[i];
            for(int j=i-1;j>=0;--j) {
                first=array[j];
                if(first*second==n) {
                    answer[0]=first;
                    answer[1]=second;
                }
            }
        }
        if ((answer[0] == 0) && (answer[1]==0)) return "[]";
        else return Arrays.toString(answer);
    }

    //5

    public static int[] IsExact(int number) {
        int[] answer = new int[0];
        int val = HelpIsExact(number,2);
        if(val!=-1) answer=new int[] {number,val};
        return answer;
    }

    public static int HelpIsExact(int val, int k) {
        if(val==1) return k-1;
        if(val%k!=0) return -1;
        return HelpIsExact(val/k,k+1);
    }

    //6

    public static String Fractions(String chislo) {
        //Чтобы записать смешанную периодическую дробь в виде обыкновенной, нужно из числа, которое стоит до второго периода вычесть число,
        // стоящее до первого периода, и записать результат в числителе. А в знаменатель нужно поставить число,
        // которое содержит столько девяток, сколько цифр в периоде, нулей в конце и сколько цифр между запятой и периодом.
        int dotIndex = chislo.indexOf(".");
        int bracketIndex = chislo.indexOf("(");
        int wholePart = Integer.parseInt(chislo.substring(0, dotIndex));
        String unrepeatPart = chislo.substring(dotIndex + 1, bracketIndex);
        String repeatPart = chislo.substring(bracketIndex + 1, chislo.length() - 1);
        int firstBit = 0;
        if (unrepeatPart.length() > 0) firstBit = Integer.parseInt(unrepeatPart);
        int chisl = Integer.parseInt(unrepeatPart+repeatPart) - firstBit;
        String znam_str = "";
        for (int i=0; i < repeatPart.length(); i++) znam_str += "9";
        for (int i=0; i < unrepeatPart.length(); i++) znam_str += "0";
        int znam = Integer.parseInt(znam_str);
        int k = 2;
        int t = Math.max(chisl, znam);
        while (k < t) { //блок для сокращения
            if (chisl % k == 0 && znam % k == 0) {
                chisl /= k;
                znam /= k;
            }else k++;
        }
        return Integer.toString(wholePart*znam + chisl) + "/" + Integer.toString(znam);
    }

    //7

    public static String PilishString(String s) {
        String  Pi = "314159265358979";
        String ans = "";
        if (s.length() == 0) return ans;
        int currentIndex = 0;
        for(int i=0; i<Pi.length(); i++) {
            int bitSize = Integer.parseInt(Pi.substring(i, i+1));
            int sLostLen = s.substring(currentIndex, s.length()).length();
            if (sLostLen == 0) break;
            if (sLostLen < bitSize) {
                String repeat = s.substring(s.length()-1); //begin index
                String dop = repeat;
                ans += s.substring(currentIndex, s.length());
                for (int j=1; j < bitSize - sLostLen ; j++ ) dop += repeat;
                ans += dop;
                break;
            }else ans += s.substring(currentIndex, currentIndex + bitSize) + " ";
            currentIndex += bitSize;
        }
        return ans;
    }

    //8

    public static String generateNonconsecutive(int binary_chislo) {
        return recurse(binary_chislo,false,"");
    }

    public static String recurse(int binary_chislo, boolean isOne,String s) {
        if(binary_chislo == 1) {
            if(isOne) return s + "0 ";
            else return s + "0" + " " + s + "1 ";
        }
        if (isOne) return recurse(binary_chislo - 1, false, s + "0");
        else return recurse(binary_chislo - 1, false, s + "0") + recurse(binary_chislo - 1, true, s + "1");
    }

    //9

    public static String isValid(String s1) {
        Map<String, Integer> dictionary = new HashMap<String, Integer>();
        for (int i=0; i< s1.length(); i++) {
            String sym = s1.substring(i, i+1);
            if (dictionary.keySet().contains(sym)) {
                int old = dictionary.get(sym);
                dictionary.replace(sym, old, old+1);
            }else dictionary.put(sym, 1);
        }
        int count = 0;
        int sum = 0;
        int mis = 0;
        String ans = "YES";
        for (int value: dictionary.values()) {
            if (count != 0 && Math.abs(sum / count - value) > 1 ) {
                ans = "NO";
                break;
            }
            sum+= value;
            count++;
            if ( (double) sum/ count != sum / count) {
                mis++;
                sum -= value;
                count--;
                if (mis > 1) {
                    ans = "NO";
                    break;
                }
            }
        }
        return ans;
    }

    //10

    public static List<List<Integer>> sumsUp(int[] input) {
        List<List<Integer>> pairs = new ArrayList<>();

        for (int i = input.length - 1; i >= 0; i--) {
            for (int j = i; j >= 0; j--) {
                if (j != i && (input[i] + input[j]) == 8) {
                    List<Integer> pair = new ArrayList<>(List.of(input[i], 8 - input[i])); //List.of возвращает список
                    pair.sort(Integer::compare); //сравнивает и сортирует
                    pairs.add(pair);
                }
            }
        }
        Collections.reverse(pairs);
        return pairs;
    }


    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);

        //1
        System.out.println(hiddenAnagram("My world evolves in a beautiful space called Tesh.", "sworn love lived"));
        System.out.println(hiddenAnagram("An old west action hero actor", "Clint Eastwood"));

        //2
        System.out.println(collect("intercontinentalisationalism", 6));
        System.out.println(collect("strengths", 3));

        //3

        System.out.println(NicoCipher("myworldevolvesinhers", "tesh"));
        System.out.println(NicoCipher("andiloveherso", "tesha"));

        //4

        System.out.println(TwoProduct(new int[] {100,12, 4, 1, 2}, 15));
        System.out.println(TwoProduct(new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10}, 10));

        //5

        System.out.println(IsExact(6));
        System.out.println(IsExact(1024));
        System.out.println(IsExact(24));

        //6

        System.out.println(Fractions("0.(6)"));
        System.out.println(Fractions("0.19(2367)"));
        System.out.println(Fractions("3.(142857)"));

        //7

        System.out.println(PilishString("HOWINEEDADRINKALCOHOLICINNATUREAFTERTHEHEAVYLECTURESINVOLVINGQUANTUMMECHANICSANDALLTHESECRETSOFTHEUNIVERSE"));
        System.out.println(PilishString("FORALOOP"));
        System.out.println(PilishString("CANIMAKEAGUESSNOW"));
        System.out.println(PilishString("33314444"));

        //8

        System.out.println(generateNonconsecutive(scan.nextInt()));
        System.out.println(generateNonconsecutive(scan.nextInt()));
        System.out.println(generateNonconsecutive(scan.nextInt()));

        //9

        System.out.println(isValid("aabbcd"));
        System.out.println(isValid("aabbccddeefghi"));

        //10

        System.out.println(sumsUp(new int[] { 1, 2, 3, 4, 5 }));
        System.out.println(sumsUp(new int[] { 1, 2, 3, 7, 9 }));
    }
}

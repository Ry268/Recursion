// 文字列の圧縮
/*Hodge は文章を短く表示するアプリを作成しており、文字が連続して 2 回以上続く場合は文字を数字に置き換えようと考えています。
アルファベットで書かれた文字列 s が与えられるので、再帰を使って連続で続いた文字を数字に置き換える、stringCompression という関数を作成してください。 */

class Main{
    public static String stringCompression(String s){
        // 関数を完成させてください
        return stringCompressionHelper(s, 0, 0, "");
    }

    public static String stringCompressionHelper(String s, int left, int right, String output) {
        // left が文字列より大きくなった場合は再帰を終了
        if (left >= s.length()) {
            return output;
        }
        if (right < s.length() && s.charAt(left) == s.charAt(right)) {
            return stringCompressionHelper(s, left, right+1, output);
        }
        int count = right - left;
        if (count > 1) {
            output += s.charAt(left) + String.valueOf(count);
        }
        else {
            output += s.charAt(left);
        }
        left = right;
        return stringCompressionHelper(s, left, right, output);
    }

    public static void main(String[] args){
        System.out.println(stringCompression("aaabbb"));
        System.out.println(stringCompression("aaabbbc"));
        System.out.println(stringCompression("aaabbbCCL"));
        System.out.println(stringCompression("aaabbbCCLL"));
    }
}
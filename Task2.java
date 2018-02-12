import java.util.*;
import java.lang.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


class Task2 {

    private static String operators = "+-*/^";
    private static String delimiters = "() " + operators;
    private static boolean flag = true;
    private static boolean isDelimiter(String token) {
        if (token.length() != 1) return false;
        for (int i = 0; i < delimiters.length(); i++) {
            if (token.charAt(0) == delimiters.charAt(i)) return true;
        }
        return false;
    }

    private static boolean isOperator(String token) {
        if (token.equals("u-")) return true;
        for (int i = 0; i < operators.length(); i++) {
            if (token.charAt(0) == operators.charAt(i)) return true;
        }
        return false;
    }



    private static int priority(String token) {
        if (token.equals("(")) return 1;
        if (token.equals("+") || token.equals("-")) return 2;
        if (token.equals("*") || token.equals("/")) return 3;
        if(token.equals("^"))return 4;
        return 5;
    }

    private static List<String> parse(String infix) {
        List<String> postfix = new ArrayList<>();
        Deque<String> stack = new ArrayDeque<>();
        StringTokenizer tokenizer = new StringTokenizer(infix, delimiters, true);
        String prev = "";

        while (tokenizer.hasMoreTokens()) {
            String curr = tokenizer.nextToken();
            if (!tokenizer.hasMoreTokens() && isOperator(curr)) {
                System.out.println("Некорректное выражение.");
                flag = false;
                return postfix;
            }
            if (curr.equals(" ")) continue;

            else if (isDelimiter(curr)) {
                if (curr.equals("(")) {
                    stack.push(curr);
                }
                else if (curr.equals(")")) {
                    while (!stack.peek().equals("(")) {
                        postfix.add(stack.pop());
                        if (stack.isEmpty()) {
                            System.out.println("Скобки не согласованы.");
                            flag = false;
                            return postfix;
                        }
                    }
                    stack.pop();

                }
                else {
                    if (curr.equals("-") && (prev.equals("") || (isDelimiter(prev)  && !prev.equals(")")))) {
                     curr = "u-";
                    }
                    else
                        {
                        while (!stack.isEmpty() && (priority(curr) <= priority(stack.peek()))) {
                            postfix.add(stack.pop());
                        }

                    }
                    stack.push(curr);
                }

            }

            else {
                postfix.add(curr);
            }
            prev = curr;
        }

        while (!stack.isEmpty()) {
            if (isOperator(stack.peek())) postfix.add(stack.pop());
            else {
                System.out.println("Скобки не согласованы.");
                flag = false;
                return postfix;
            }
        }
        return postfix;
    }


    private static Double calc(List<String> postfix) {
        Deque<Double> stack = new ArrayDeque<>();
        for (String x : postfix) {
                if (x.equals("+")) {
                    stack.push(stack.pop() + stack.pop());
                }
            else if (x.equals("-")) {
                Double b = stack.pop(), a = stack.pop();
                stack.push(a - b);
            }
            else if (x.equals("*")) stack.push(stack.pop() * stack.pop());
            else if (x.equals("/")) {
                Double b = stack.pop(), a = stack.pop();
                if (b==0){
                    System.out.println("Деление на ноль");
                    System.exit(0);

                }
                stack.push(a / b);
            }
            else if (x.equals("^")){
                double b=stack.pop(),a=stack.pop();
                stack.push(Math.pow(a,b));
            }
            else if (x.equals("u-")) stack.push(-stack.pop());
            else stack.push(Double.valueOf(x));
        }
        return stack.pop();
    }

    public static void main (String[] args) {
        Scanner in = new Scanner(System.in);
        String s = in.nextLine();
        Task2 n = new Task2();
        if (n.regex(s)){
            System.out.println("Невалидные данные");
        }
        else {
           List<String> expression = parse(s);

        if (flag) {
            for (String x : expression) System.out.print(x + " ");
            System.out.println();
            System.out.println(calc(expression));
        }
        }
    }
    private boolean regex(String in){

         Pattern p = Pattern.compile("((\\d\\s\\d)|[a-zA-Z])");
         Matcher m = p.matcher(in);


            return m.find();
    }
}

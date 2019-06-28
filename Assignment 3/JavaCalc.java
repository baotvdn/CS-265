/**
 * @author Bao Dang
 * @version 9.0
 * @since 2017-11-28
 * Platform: Linux Workstation (tux)
 *   
 * <h1>Java Calculator</h1>
 * A program that parses infix expressions into appropriate Tokens (operator or operand),
 * stored in some linear container , passes the infix expression to a function that returns the expression to
 * postfix form, then passes it to a function which evaluates the postfix expression, returns an integer.
 * <p>
 * <b>Note:</b> The program handles integer arithmetic. No float types. Also, though the inputs are positive, the intermediate results might not be.
 * The operators you will encounter are +, -, *, /, % (modulus). You must also be prepared to handle parenthesis.
 */
 import java.io.*;
 import java.util.*;
 public class JavaCalc{
	/**
	* This method checks if the token is an open parentheses
	* @param token The input token
	* @return boolean
	*/
	public static boolean openParentheses(Token token)
	{
		return ((Operator)token).getVal().getName().equals("(");
	}
	/**
	* This method checks if the token is an close parentheses
	* @param token The input token
	* @return boolean
	*/
	public static boolean closeParentheses(Token token)
	{
		return ((Operator)token).getVal().getName().equals(")");
	}
	/**
	* This method checks if the precedence of current token is less than or equal to the precedence of peek token of the stack
	* @param currentToken The current token
	* @param stackPeek The peek token of the stack
	* @return boolean
	*/
	public static boolean precedenceDetect(Token currentToken, Token stackPeek)
	{
		return (Operator.compare((Operator)currentToken, (Operator)stackPeek) == 0 || Operator.compare((Operator)currentToken, (Operator)stackPeek) == -1);
	}
  /**
	* The method parses lines in the file to convert the operators in string into opType form.
	* @param line the input expression in string
	* @return an array list of token
	*/
	public static ArrayList<Token> parseInfix(String line){
		ArrayList<Token> tokens = new ArrayList<Token>();
		StringTokenizer tk = new StringTokenizer(line," ");
		while(tk.hasMoreTokens()){
			String symbol = tk.nextToken();
			switch(symbol){
				case "+":
					tokens.add( new Operator(opType.ADD) );
					break;
				case "-":
					tokens.add( new Operator(opType.SUB) );
					break;
				case "*":
					tokens.add( new Operator(opType.MULT) );
					break;
				case "/":
					tokens.add( new Operator(opType.DIV) );
					break;
				case "%":
					tokens.add( new Operator(opType.MOD) );
					break;
				case "(":
					tokens.add( new Operator(opType.LPAR) );
					break;
				case ")":
					tokens.add( new Operator(opType.RPAR) );
					break;
				default:
					tokens.add( new Operand(Integer.parseInt(symbol)) );
					break;
		 }
		}
		return tokens;
	}
	
	/**
	*The method converts infix to postfix
	* @param infix the array list of input token
	* @return postfix stack
	*/
	public static Stack<Token> infix2postfix(ArrayList<Token> infix){
		Stack<Token> postfix = new Stack<Token>();
		Stack<Token> opStack = new Stack<Token>();
		for(Token token : infix){
			if(token.isOperand()){
				postfix.push(token);
			}
			else if(openParentheses(token)){
				opStack.push(token);
			}
			else if(closeParentheses(token)){
				while (!openParentheses(opStack.peek())) {
					postfix.push(opStack.pop());
				}
				opStack.pop();
			}
			else if (token.isOperator()){
				while(!opStack.isEmpty() && precedenceDetect(token, opStack.peek())) {
					postfix.push(opStack.pop());
				}
				opStack.push(token);
			}
		}
		while (!opStack.isEmpty()) {
			postfix.push(opStack.pop());
		}
		return postfix;
	}
	
	/**
	* The method evaluates the postfix expression, then show the result of the expression.
	* The evaluation stack should have only one token. If it has more than one, the stack will
	* pop all tokens to get errors.
	* @param postfix the postfix input stack
	* @return the integer evaluation results. If the evaluation stack has more than 1 tokens, an empty
	* stack error will come out.
	*/
	public static int evalPostfix(Stack<Token> postfix){
		Stack<Token> evaluation = new Stack<>();
		for (Token tokens : postfix){
			if(tokens.isOperand())
				evaluation.push(tokens);
			else{
				String operator = (((Operator)tokens).getVal().getName());
				int y = ((Operand)evaluation.pop()).getVal();
				int x = ((Operand)evaluation.pop()).getVal();
				switch (operator) {
					case "+":
						evaluation.push(new Operand(x + y));
						break;
					case "-":
						evaluation.push(new Operand(x - y));
						break;
					case "*":
						evaluation.push(new Operand(x * y));
						break;
					case "/":
						evaluation.push(new Operand(x / y));
						break;
					case "%":
						evaluation.push(new Operand(x % y));
						break;
					default:
						throw new IllegalArgumentException("Unknown Operator: " + operator);
				}
			}
		}
		if (evaluation.size() != 1){
			while (!evaluation.isEmpty()){
				evaluation.pop();
			}
		}
	return ((Operand)evaluation.pop()).getVal();
	}
	
	/**
	* This is the main method which makes use of all of the methods above and print the outputs.
	* @param args Unused
	* @exception  IOException On input error
	* @see IOException
	*/
	public static void main(String [] args)throws IOException{
		File infixFile = new File(args[0]);
		Scanner input = new Scanner(infixFile);
		if (input.hasNextLine()) {
			while (input.hasNextLine()) {
				String line = input.nextLine();
				ArrayList<Token> infix = parseInfix(line);
				Stack<Token> postfix = infix2postfix(infix);
				for (Token token : postfix) {
					if (token.isOperator()) {
						System.out.print(((Operator)token).getVal().getName() + " ");
					} 
					else {
						System.out.print(((Operand)token).getVal() + " ");
					}
				}
				try {
					System.out.println("= " + evalPostfix(postfix));
				}
				catch (ArithmeticException e){
					System.out.println("Error: " + e);
				}
			}
		}
		else{
			System.out.println("There is something wrong with the input file!");
		}
	}
}

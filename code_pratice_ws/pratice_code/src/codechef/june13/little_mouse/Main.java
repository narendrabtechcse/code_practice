package codechef.june13.little_mouse;

import java.io.*;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

public class Main {
	public static void main(String[] s) {
		try {
			InputStream inputStream = System.in;
			InputReader in = new InputReader(inputStream);
			PrintWriter writer = new PrintWriter(System.out);

			TaskA solution = new TaskA();
			solution.solve(in,writer);

			writer.close();
			inputStream.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

class TaskA {

	public void solve(InputReader in, PrintWriter out) throws IOException {

		int tests = in.nextInt();

		for (int i=0;i<tests;i++) {

			int row = in.nextInt();
			int column = in.nextInt();

			int[][] board = new int[row][column];

			for (int r=0;r<row;r++) {
				char[] str = in.next().toCharArray();

				for (int c=0;c<column;c++) {
					board[r][c] = Integer.parseInt(str[c]+"");
				}
			}

			int minScare = getMinScarePossible(board, row, column);

			out.println(minScare);
		}

	}

	private int getMinScarePossible(int[][] board, int row, int column) {
		Set<Node>[][] boardPath = new Set[row][column];

		// initialize origin (0,0)
		boardPath[0][0] = new HashSet<Node>();
		if (board[0][1]==1) {
			addToSet(boardPath[0][0], 0, 1);
		}
		if (board[1][0]==1) {
			addToSet(boardPath[0][0], 1, 0);
		}

		// start to last-but-one-row and last-but-one-column.
		for (int i=0;i<row-1;i++) {
			for (int j=0;j<column-1;j++) {

				if (i+j==0)
					continue;

				else if (i==0) {
					Node node1 = new Node(i,j+1);
					Node node2 = new Node(i+1,j);
					Set<Node> setx = boardPath[i][j-1];
					Set<Node> newSet = getNodeSetFromPath(setx, board, node1, node2);
					boardPath[i][j] = newSet;
				} else if (j==0) {
					Node node1 = new Node(i,j+1);
					Node node2 = new Node(i+1,j);
					Set<Node> sety = boardPath[i-1][j];
					Set<Node> newSet = getNodeSetFromPath(sety, board, node1, node2);
					boardPath[i][j] = newSet;
				} else {
					// normal case
					Node node1 = new Node(i,j+1);
					Node node2 = new Node(i+1,j);

					Node nodex = new Node(i,j-1);
					Node nodey = new Node(i-1,j);

					Set<Node> setx = boardPath[i][j-1];
					Set<Node> sety = boardPath[i-1][j];

					int pathx = boardPath[i][j-1].size();
					if (board[i-1][j]==1 && !boardPath[i][j-1].contains(nodey))
						pathx++;

					int pathy = boardPath[i-1][j].size();
					if (board[i][j-1]==1 && !boardPath[i-1][j].contains(nodex))
						pathy++;

					// form the new Set Node with the new nodes.
					if (pathx < pathy) {
						Set<Node> newSet = getNodeSetFromPath(setx, board, nodey, node1, node2);
						boardPath[i][j] = newSet;
					} else {
						Set<Node> newSet = getNodeSetFromPath(sety, board, nodex, node1, node2);
						boardPath[i][j] = newSet;
					}
				}
			}
		}

		// row-1,0
		{
			int i=row-1;
			int j=0;
			Node node1 = new Node(i,j+1);
			Set<Node> sety = boardPath[i-1][j];
			Set<Node> newSet = getNodeSetFromPath(sety, board, node1);
			boardPath[i][j] = newSet;
		}

		// 0,column-1

		{
			int i=0;
			int j=column-1;
			Node node2 = new Node(i+1,j);
			Set<Node> setx = boardPath[i][j-1];
			Set<Node> newSet = getNodeSetFromPath(setx, board, node2);
			boardPath[i][j] = newSet;
		}

		// last row
		for (int j=1,i=row-1;j<column-1;j++) {
			Node node1 = new Node(i,j+1);


			Node nodex = new Node(i,j-1);
			Node nodey = new Node(i-1,j);

			Set<Node> setx = boardPath[i][j-1];
			Set<Node> sety = boardPath[i-1][j];

			int pathx = boardPath[i][j-1].size();
			if (board[i-1][j]==1 && !boardPath[i][j-1].contains(nodey))
				pathx++;

			int pathy = boardPath[i-1][j].size();
			if (board[i][j-1]==1 && !boardPath[i-1][j].contains(nodex))
				pathy++;

			// form the new Set Node with the new nodes.
			if (pathx < pathy) {
				Set<Node> newSet = getNodeSetFromPath(setx, board, nodey, node1);
				boardPath[i][j] = newSet;
			} else {
				Set<Node> newSet = getNodeSetFromPath(sety, board, nodex, node1);
				boardPath[i][j] = newSet;
			}
		}

		// last column
		for (int i=1, j=column-1;i<row-1;i++) {
			Node node1 = new Node(i+1,j);

			Node nodex = new Node(i,j-1);
			Node nodey = new Node(i-1,j);

			Set<Node> setx = boardPath[i][j-1];
			Set<Node> sety = boardPath[i-1][j];

			int pathx = boardPath[i][j-1].size();
			if (board[i-1][j]==1 && !boardPath[i][j-1].contains(nodey))
				pathx++;

			int pathy = boardPath[i-1][j].size();
			if (board[i][j-1]==1 && !boardPath[i-1][j].contains(nodex))
				pathy++;

			// form the new Set Node with the new nodes.
			if (pathx < pathy) {
				Set<Node> newSet = getNodeSetFromPath(setx, board, nodey, node1);
				boardPath[i][j] = newSet;
			} else {
				Set<Node> newSet = getNodeSetFromPath(sety, board, nodex, node1);
				boardPath[i][j] = newSet;
			}
		}

		// last column/row --> destination.
		int i=row-1;
		int j=column-1;
		Set<Node> setx = boardPath[i][j-1];
		Set<Node> sety = boardPath[i-1][j];

		int minScare = Math.min(setx.size(), sety.size());

		boolean trial = true;
		if (trial) {
			if (setx.size() > sety.size()) {
				boardPath[i][j] = sety;
			} else {
				boardPath[i][j] = setx;
			}
			printBoardPath(boardPath, row, column);
		}

		return minScare;
	}

	private void printBoardPath(Set<Node>[][] boardPath, int row, int column) {
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < column; j++) {
				System.out.print(boardPath[i][j].size() + "\t");
			}
			System.out.println("");
		}
	}

	private Set<Node> getNodeSetFromPath(Set<Node> set, int[][] board, Node...nodes) {
		Set<Node> newSet = new HashSet<Node>(set);
		for (Node node : nodes) {
			if (board[node.x][node.y]==1) {
				newSet.add(node);
			}
		}

		return newSet;
	}

	private void addToSet(Set<Node> set, int row, int column) {
		Node node = new Node(row, column);
		set.add(node);
	}


}

class Node {
	int x;
	int y;

	public Node(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Node node = (Node) o;

		if (x != node.x) return false;
		if (y != node.y)  return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = x;
		result = 31 * result + y;
		return result;
	}
}

class InputReader {
	public BufferedReader reader;
	public StringTokenizer tokenizer;

	public InputReader(InputStream stream) {
		reader = new BufferedReader(new InputStreamReader(stream));
		tokenizer = null;
	}

	public String next() {
		while (tokenizer == null || !tokenizer.hasMoreTokens()) {
			try {
				tokenizer = new StringTokenizer(reader.readLine());
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		return tokenizer.nextToken();
	}

	public int nextInt() {
		return Integer.parseInt(next());
	}

	public long nextLong() {
		return Long.parseLong(next());
	}

	public double nextDouble() {
		return Double.parseDouble(next());
	}

}

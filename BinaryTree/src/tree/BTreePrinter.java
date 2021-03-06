package tree;
/** Author: michal.kreuzman 
 * http://stackoverflow.com/questions/4965335/how-to-print-binary-tree-diagram */
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import tree.BST.Node;

@SuppressWarnings("rawtypes")
class BTreePrinter {
	static FileWriter fw;
	public static <T extends Comparable<?>> void printNode(Node root) throws IOException {
		fw = new FileWriter(new File("output.txt"));
		int maxLevel = BTreePrinter.maxLevel(root);
		printNodeInternal(Collections.singletonList(root), 1, maxLevel);
		fw.flush();
		fw.close();
	}

	private static <T extends Comparable<?>> void printNodeInternal(
			List<Node> nodes, int level, int maxLevel) throws IOException {
		if (nodes.isEmpty() || BTreePrinter.isAllElementsNull(nodes))
			return;

		int floor = maxLevel - level;
		int endgeLines = (int) Math.pow(2, (Math.max(floor - 1, 0)));
		int firstSpaces = (int) Math.pow(2, (floor)) - 1;
		int betweenSpaces = (int) Math.pow(2, (floor + 1)) - 1;

		BTreePrinter.printWhitespaces(firstSpaces);

		List<Node> newNodes = new ArrayList<Node>();
		for (Node node : nodes) {
			if (node != null) {
				fw.write(""+node.key);
				newNodes.add(node.left);
				newNodes.add(node.right);
			} else {
				newNodes.add(null);
				newNodes.add(null);
				fw.write(" ");
			}

			BTreePrinter.printWhitespaces(betweenSpaces);
		}
		fw.write("\n");
		for (int i = 1; i <= endgeLines; i++) {
			for (int j = 0; j < nodes.size(); j++) {
				BTreePrinter.printWhitespaces(firstSpaces - i);
				if (nodes.get(j) == null) {
					BTreePrinter.printWhitespaces(endgeLines + endgeLines + i
							+ 1);
					continue;
				}

				if (nodes.get(j).left != null)
					fw.write("/");
				else
					BTreePrinter.printWhitespaces(1);

				BTreePrinter.printWhitespaces(i + i - 1);

				if (nodes.get(j).right != null)
					fw.write("\\");
				else
					BTreePrinter.printWhitespaces(1);

				BTreePrinter.printWhitespaces(endgeLines + endgeLines - i);
			}

			fw.write("\n");
		}

		printNodeInternal(newNodes, level + 1, maxLevel);
	}

	private static void printWhitespaces(int count) throws IOException {
		for (int i = 0; i < count; i++)
			fw.write(" ");
	}

	private static <T extends Comparable<?>> int maxLevel(Node node) {
		if (node == null)
			return 0;

		return Math.max(BTreePrinter.maxLevel(node.left),
				BTreePrinter.maxLevel(node.right)) + 1;
	}

	private static <T> boolean isAllElementsNull(List<T> list) {
		for (Object object : list) {
			if (object != null)
				return false;
		}

		return true;
	}

}
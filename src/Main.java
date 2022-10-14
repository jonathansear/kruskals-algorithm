import java.util.ArrayList;
import java.util.HashSet;

public class Main {

    private static final int[][] _GraphMap = new int[][] {
            // Node, Node, Cost
            { 1, 2, 8 },
            { 1, 3, 2 },
            { 1, 4, 5 },
            { 2, 4, 2 },
            { 2, 6, 13 },
            { 3, 4, 2 },
            { 3, 5, 5 },
            { 4, 6, 6 },
            { 4, 7, 3 },
            { 4, 5, 1 },
            { 5, 7, 1 },
            { 6, 7, 2 },
            { 6, 8, 3 },
            { 7, 8, 6 },
    };

    private static final int Nodes = 8;

    private static final ArrayList<Node> _Edges = new ArrayList<Node>();
    private static ArrayList<Vertex> _Vertices = new ArrayList<>();
    private static ArrayList<HashSet<Vertex>> _Subsets = new ArrayList<>();

    public static void main(String[] args) {
        setup();
        sortNodesOnCost();
        DoKruskal();
    }

    private static void DoKruskal() {
        // add the first node to the subset
        for (int i = 0; i < _Vertices.size(); i++) {
            HashSet<Vertex> set = new HashSet<>();
            set.add(_Vertices.get(i));
            _Subsets.add(set);
        }

        for (Node node : _Edges) {
            Vertex srcNode = _Vertices.get(node.source);
            Vertex destNode = _Vertices.get(node.destination);
            // check if the source and destination are in the same subset
            // if they are, then skip this edge
            // if they are not, then add this edge to the spanning tree
            if (find(srcNode) != find(destNode)) {
                System.out.println(node.source + " --> " + node.destination + " == " + node.cost);
                union(find(srcNode), find(destNode));
            }
        }
    }

    private static void union(int src, int dest) {
        // merge two subsets into one subset
        HashSet<Vertex> srcSet = _Subsets.get(src);
        HashSet<Vertex> destSet = _Subsets.get(dest);
        srcSet.addAll(destSet);
        _Subsets.set(dest, srcSet);
    }

    private static int find(Vertex node) {
        // find the subset that contains the node
        for (int i = 0; i < _Subsets.size(); i++) {
            HashSet<Vertex> set = _Subsets.get(i);
            if (set.contains(node)) {
                return i;
            }
        }
        return -1;
    }

    private static void sortNodesOnCost() {
        for (int i = 0; i < _Edges.size(); i++) {
            for (int j = 0; j < _Edges.size(); j++) {
                if (_Edges.get(i).cost < _Edges.get(j).cost) {
                    Node temp = _Edges.get(i);
                    _Edges.set(i, _Edges.get(j));
                    _Edges.set(j, temp);
                }
            }
        }
    }

    private static void setup() {
        // create the vertices
        for (int i = 0; i < Nodes + 1; i++) {
            _Vertices.add(new Vertex(i));
        }
        for (int[] node : _GraphMap) {
            _Edges.add(new Node(node[0], node[1], node[2]));
        }
    }
}
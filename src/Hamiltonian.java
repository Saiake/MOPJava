import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

class Hamiltonian {
    private char vertices[];
    private int adjacencyM[][];
    private int visited[];
    private int start;
    private Stack<Integer> cycle = new Stack<>();
    private int N;
    private boolean hasCycle = false;

    static String alphabet = "ABCDEFGHIJKLMNOPRSTU";

    Hamiltonian(int start, char vertices[], int adjacencyM[][]) {
        this.start = start;
        this.vertices = vertices;
        this.adjacencyM = adjacencyM;
        this.N = vertices.length;
        this.visited = new int[vertices.length];
    }

    public boolean hasCycle() {
        return hasCycle;
    }

    public void findPath() {
        cycle.push(start);
        solve(start);
    }

    private void solve(int vertex) {
        List<Integer> l = cycle.stream().toList();

        if (cycle.size() >= N && l.stream().distinct().count() == cycle.size()) {
            hasCycle = true;

            displayCycle();

            return;
        }

        for (int i = 0; i < N; i++) {
            if (adjacencyM[vertex][i] == 1 && visited[i] == 0) {
                int nbr = i;
                visited[nbr] = 1;

                cycle.push(nbr);

                solve(nbr);

                visited[nbr] = 0;
                cycle.pop();
            }
        }
    }

    void displayCycle() {
        List<Character> names = new ArrayList<>();

        for (int idx : cycle) {
            names.add(vertices[idx]);
        }

        System.out.println(names);
    }

    public static int[][] inputGraph() {
        System.out.println("Введите количество вершин в графе: ");

        Scanner sc = new Scanner(System.in);

        int V;

        do {
            while (!sc.hasNextInt()) {
                System.out.println("Пожалуйста, попробуйте ещё раз!");

                sc.next();
            }
            V = sc.nextInt();
            if (V <= 0) {
                System.out.println("Пожалуйста, попробуйте ещё раз!");
            }
        } while(V <= 0);
        sc.nextLine();

        int[][] graph = new int[V][V];

        System.out.println("Введите матрицу смежности через запятую: ");

        for (int i = 0; i < V; i++) {
            boolean GO = true;

            while (GO) {
                System.out.print("Узел " + (i + 1) + ": ");

                String str = sc.next();
                String[] strA = str.split(",");

                if (strA.length < V) {
                    System.out.println("Ошибка ввода! Попробуйте ещё раз");
                }

                else {
                    for (int j = 0; j < V; j++) {
                        try{
                            int value;

                            value = Integer.parseInt(strA[j]);

                            graph[i][j] = value;

                        } catch(NumberFormatException e) {
                            System.out.println("Ошибка ввода! Попробуйте ещё раз");

                            graph[i] = new int[V];

                            break;
                        }
                        GO = false;
                    }
                }
            }
        }
        return graph;
    }

    public static void main(String[] args) {
        boolean metka = true;
        while (metka) {
            int[][] graph = inputGraph();

            Hamiltonian hamiltonian = new Hamiltonian(0, alphabet.substring(0, graph.length).toCharArray(), graph);
            hamiltonian.findPath();

            if (!hamiltonian.hasCycle()) {
                System.out.println("Невозможно построить цепь");
            }

            System.out.println("Продолжить работу? Y/N");

            Scanner sc = new Scanner(System.in);

            String answ = sc.next();
            if (answ.equals("N") || answ.equals("n")) {
                metka = false;
            }
            else if (answ.equals("Y") || answ.equals("y")) {
                metka = true;
            }
            else {
                metka = true;
            }
        }
    }
}
import java.util.*;

class Edge implements Comparable{
    private double weight;
    public Vertex from;
    public Vertex to;


    Edge(Vertex from, Vertex to, double weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }

    public Vertex getFrom() {
        return this.from;
    }

    public Vertex getTo() {
        return this.to;
    }

    public double getWeight() {
        return this.weight;
    }

    public String toString() {
        return "From: " + from.getName() + " To: " + to.getName();
    }



    /**
     * Created with the help of the DistEntry class in the HW2 PDF.
     * @param o The object to be casted to an Edge and compared
     * @return
     */
    //Created with the help of the DistEntry in the HW2 PDF
    @Override
    public int compareTo(Object o) {
        if (!(o instanceof Edge)) return 0; //object is not actually an Edge, type check is in here rather than
        Edge other = (Edge) o;
        if (this.weight > other.weight) return 1;
        else if (this.weight < other.weight) return -1;
        else return 0;
    }
}

class Vertex {
    private String vertexName;
    private double x;
    private double y;
    private double z;
    private double distance = Double.POSITIVE_INFINITY;
    public Vertex pred = null;

    public Vertex (String vertexName, double x, double y, double z) {
        this.vertexName = vertexName;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    String getName() {
        return this.vertexName;
    }

    public ArrayList<Double> getCoordinates() {
        return new ArrayList<>(Arrays.asList(x, y, z));
    }

    public void setDistance(Double d) {
        this.distance = d;
    }

    public double getDistance() {
        return this.distance;
    }

    public void setPredecessor(Vertex p) {
        this.pred = p;
    }

}





public class Dijkstra {
    /*
        Psuedocode:
            Scan the inputs into a data structure that holds the string of the vertex and the three x, y, z coordinates.
            Calculate the distances between the source and all of the vertices
            Initialize all distances away from vertices as a very large number (Integer.MAX_VALUE)
            Add all vertices to the priority queue
            While the priority queue is not empty:
                Extract the minimum value vertex
                Get all the neighbors of the vertex
                    //neighbors are calculated by: seeing which vertices have a distance between them of 3 or less
                For all neighbors:
                    Compare the shortest distances with all adjacent vertices
                    If any distance is less than the shortest distance on the current vertex:
                        Update the adjacent vertex shortest distance in the priority queue
                        //keep track of the distance so far...? how??

    */

    public HashMap<Vertex, LinkedList<Edge>> adjacencyList = new HashMap<>();



    public double calculateEdgeDistance(Vertex from, Vertex to) {
        ArrayList<Double> fromCoordinateList = from.getCoordinates();
        ArrayList<Double> toCoordinateList = to.getCoordinates();
        double distance = 0;
        for (int k = 0; k < fromCoordinateList.size(); k++) {
            double toMinusFrom = toCoordinateList.get(k) - fromCoordinateList.get(k);
            distance += Math.pow(toMinusFrom, 2);
        }
        return Math.sqrt(distance);
    }

    //Runs Dijkstra's algorithm
    //updates things as needed
    public ArrayList<Vertex> calculateShortestPaths(Vertex source) {
        PriorityQueue<Edge> queue = new PriorityQueue<>();
        ArrayList<Vertex> shortestPaths = new ArrayList<>();
        source.setDistance(0.0);
        queue.addAll(adjacencyList.get(source));
        while (!queue.isEmpty()) {
            Edge min = queue.poll();
            if (min.to.pred == null) {
                min.to.setDistance(min.from.getDistance() + min.getWeight());
                min.to.setPredecessor(min.from);
                queue.addAll(adjacencyList.get(min.to));
            }

            else if(min.to.getDistance() > min.from.getDistance() + min.getWeight()) {

                min.to.setDistance(min.from.getDistance() + min.getWeight());
                min.to.setPredecessor(min.from);
            }
            if (!shortestPaths.contains(min.to)) {
                shortestPaths.add(min.to);

            }
        }
        return shortestPaths;
    }

    public void addToMap(Vertex v, Edge toAdd) {
        List<Edge> lst = adjacencyList.get(v);
        if (lst == null) {
            lst = new LinkedList<>();
            lst.add(toAdd);
        }
        lst.add(toAdd);
    }

    public void printDistances(ArrayList<Vertex> distances) {
        if (distances.size() == 0) {
            System.out.println("Empty input... nothing to print!");
        }
        Collections.sort(distances, new Comparator<Vertex>() {
            @Override
            public int compare(Vertex o1, Vertex o2) {
                return o1.getDistance() != o2.getDistance() ? 
                (o1.getDistance() < o2.getDistance() ? -1 : 1) : 0;
            }
        });
        for (Vertex v : distances) {
            System.out.println(v.getName() + ":" + v.getDistance());
        }


    }

    public static void main(String[] args) {

        ArrayList<String> inputs = new ArrayList<>();
        ArrayList<Vertex> vertices = new ArrayList<>();
        Scanner scan  = new Scanner(System.in);
        Dijkstra d = new Dijkstra();
        while (scan.hasNextLine()) {
            inputs.add(scan.nextLine());
        }
        for (String s : inputs) {
            String[] split = s.split(",");
            Vertex v = new Vertex(split[0], Double.parseDouble(split[1]),
                    Double.parseDouble(split[2]), Double.parseDouble(split[3]));
            vertices.add(v);
            d.adjacencyList.put(v, new LinkedList<>());
        }

        for (int k = 0; k < vertices.size(); k++) {
            Vertex v1 = vertices.get(k);
            for (int j = k + 1; j < vertices.size(); j++) {
                Vertex v2 = vertices.get(j);
                double vertexDistance = d.calculateEdgeDistance(v1, v2);
                if (vertexDistance <= 3) {
                    Edge e1 = new Edge(v1, v2, d.calculateEdgeDistance(v1, v2));
                    d.addToMap(v1, e1);

                    if (k != 0) {
                        Edge e2 = new Edge(v2, v1, d.calculateEdgeDistance(v2, v1));
                       d.addToMap(v2, e2);

                    }
                }
            }
        }

        ArrayList<Vertex> distances = d.calculateShortestPaths(vertices.get(0));
        d.printDistances(distances);
    }

}

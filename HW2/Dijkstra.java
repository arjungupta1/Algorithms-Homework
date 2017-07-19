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

    //returns the source vertex
    public Vertex getFrom() {
        return this.from;
    }

    // returns the destination vertex
    public Vertex getTo() {
        return this.to;
    }

    // returns the edge weight
    public double getWeight() {
        return this.weight;
    }

    //conveinence method to find the source and destination nodes.
    public String toString() {
        return "From: " + from.getName() + " To: " + to.getName();
    }



    /**
     * Created with the help of the DistEntry class in the HW2 PDF.
     * @param o The object to be casted to an Edge and compared
     * @return integer representation of the relationship between two edges
     */
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
    //Init all variables
    private String vertexName;
    private double x;
    private double y;
    private double z;
    private double distance = Double.POSITIVE_INFINITY; //default instead of 0 so that source vertex can have a distance of 0
    public Vertex pred = null; //assume that initial graph is a unconnected forest

    public Vertex (String vertexName, double x, double y, double z) {
        this.vertexName = vertexName;
        this.x = x;
        this.y = y;
        this.z = z;
    }


    //return the name of this vertex
    String getName() {
        return this.vertexName;
    }

    //returns a list of the coordinates (x, y, z) used for calculating edge  distance
    public ArrayList<Double> getCoordinates() {
        return new ArrayList<>(Arrays.asList(x, y, z));
    }

    //used for changing the total distance of a vertex
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
            Create an adjacency list of all vertices and a linked list of all the possible reachable edges
            Initialize all distances away from vertices as a very large number
            Add all adjacent edges of the source to the priority queue
            While the priority queue is not empty:
                Extract the minimum weight edge
                If the destination has not been reached yet:
                    Set the distance to the total distance so far, and set the predecessor to the source node
                    Add all of the node's outgoing edges to the queue
                Relax the edge weights so that every vertex contains the minimum distance possible for the outgoing edges
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
    public ArrayList<Vertex> calculateShortestPaths(Vertex source) {
        PriorityQueue<Edge> queue = new PriorityQueue<>();
        ArrayList<Vertex> shortestPaths = new ArrayList<>();
        source.setDistance(0.0);
        source.setPredecessor(source);
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

    //conveinence method to add edges to a HashMap<Vertex, LinkedList<Edge>>
    public void addToMap(Vertex v, Edge toAdd) {
        List<Edge> lst = adjacencyList.get(v);
        if (lst == null) {
            lst = new LinkedList<>();
            lst.add(toAdd);
        }
        lst.add(toAdd);
    }

    // sorts all distances by least
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
        distances.remove(0); //removing the start node because it should never be printed and the distance is always zero.
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
                    Edge e2 = new Edge(v2, v1, d.calculateEdgeDistance(v2, v1));
                    d.addToMap(v2, e2);
                }
            }
        }

        ArrayList<Vertex> distances = d.calculateShortestPaths(vertices.get(0));
        d.printDistances(distances);
    }

}

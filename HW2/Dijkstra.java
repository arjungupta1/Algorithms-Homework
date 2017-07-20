import java.util.*;


class Vertex implements Comparable{
    //Init all variables
    private String vertexName;
    private double x;
    private double y;
    private double z;
    private double distance = Double.POSITIVE_INFINITY; //default instead of 0 so that source vertex can have a distance of 0
    public Vertex pred = null; //assume that initial graph is a unconnected forest
    public boolean done = false;

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

    /**
     * Created with the help of the DistEntry class in the HW2 PDF.
     * @param o The object to be casted to an Edge and compared
     * @return integer representation of the relationship between two edges
     */
    @Override
    public int compareTo(Object o) {
        if (!(o instanceof Vertex)) return 0; //object is not actually an Edge, type check is in here rather than
        Vertex other = (Vertex) o;
        if (this.distance > other.distance) return 1;
        else if (this.distance < other.distance) return -1;
        else return 0;
    }

}

public class Dijkstra {

    public HashMap<Vertex, LinkedList<Vertex>> adjacencyList = new HashMap<>();

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
    public void calculateShortestPaths(Vertex source) {
        PriorityQueue<Vertex> queue = new PriorityQueue<>();
        source.setDistance(0.0);
        source.setPredecessor(source);
        queue.add(source);
        List<String> output = new LinkedList<String>();
        while (!queue.isEmpty()) {
            Vertex min = queue.poll();
            output.add(min.getName() + ":" + min.getDistance());

            for (Vertex v : adjacencyList.get(min)) {
              double edgeDistance = calculateEdgeDistance(min, v);
              if (v.pred == null) {
                  v.setDistance(min.getDistance() + edgeDistance);
                  queue.offer(v);
                  v.setPredecessor(min);
              }
              else if (min.getDistance() + edgeDistance < v.getDistance()) {
                  v.setDistance(edgeDistance);
                  v.setPredecessor(min);
              }
          }
      }
      //the source will always be added to the output so adding to a list and removing the first element
      //which will always be the source allows to print out in proper order
      output.remove(0);
      for (String s : output) {
        System.out.println(s);
      }
  }
    //conveinence method to add edges to a HashMap<Vertex, LinkedList<Edge>>
    public void addToMap(Vertex v, Vertex toAdd) {
        List<Vertex> lst = adjacencyList.get(v);
        if (lst == null) {
            lst = new LinkedList<>();
            lst.add(toAdd);
        }
        lst.add(toAdd);
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

        //just connecting the graph so that all distances that are less than 3 are adjacent
        for (int k = 0; k < vertices.size(); k++) {
            Vertex v1 = vertices.get(k);
            for (int j = k + 1; j < vertices.size(); j++) {
                Vertex v2 = vertices.get(j);
                double vertexDistance = d.calculateEdgeDistance(v1, v2);
                if (vertexDistance <= 3) {
                    d.addToMap(v1, v2);
                    d.addToMap(v2, v1);
                }
            }
        }

        d.calculateShortestPaths(vertices.get(0));
    }

}

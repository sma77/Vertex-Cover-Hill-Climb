import java.io.*;
import java.util.*;
//graph
class Graph{
    int vtx;
    HashMap<Integer,List<Integer>> edge;
    
    Graph(int vtx){
        this.vtx = vtx;
        this.edge = new HashMap<>();
        for(int i=0;i<vtx;i++){
            edge.put(i+1, new ArrayList<Integer>());
        }
    }
    
    Graph(HashMap<Integer,List<Integer>> edge){
        this.vtx=edge.size();
        this.edge=new HashMap<>();
        for(int key:edge.keySet()){
            this.edge.put(key,new ArrayList<>());
            for(int i=0;i<edge.get(key).size();i++){
                this.edge.get(key).add(edge.get(key).get(i));
            }
        }
    }
    
    void addedge (String[] s, int row){
        int len = s.length;
        for (int i=0;i<len;i++){
            edge.get(row).add(Integer.parseInt(s[i]));
        }
    }
}
public class vertexcover{
    public static int s=0;
    public static List<Integer> F=new LinkedList<Integer>();
    public static void main(String[] args) throws IOException{
        long startTimeall=System.nanoTime();
        String inputdata = args[0];
        String outputdata1 = args[1];
        String outputdata2 = args[2];
        String Timecut = args[3];
        String randseed = args[4];
        PrintWriter sol,trace;
             int seed = Integer.parseInt(randseed);
        double timecut = Integer.parseInt(Timecut);
        int tmc=(int)timecut;
        for(int n=seed;n<seed+10;n++){
            
            long startTime=System.nanoTime();
            if(s==1) break;
            if((System.nanoTime()-startTimeall)/1000>600000000) break;
            
            sol = new PrintWriter(outputdata1+tmc+"_"+n+".sol", "UTF-8");
            trace = new PrintWriter(outputdata2+tmc+"_"+n+".trace", "UTF-8");
            vertexcover vc=new vertexcover();
            Graph g= vc.Readgraph(inputdata);
            Random rando = new Random(n); 
            int newmb=g.edge.size();
            List<Integer> T=new LinkedList<Integer>();
            for(int j=0;j<g.edge.size();j++){
                T.add(j);
            }
            for(int k=0;k<100;k++){                  //the number of loop you want to process the algorithm with the random seed.
                long startTime1=System.nanoTime();
                if(s==1)  break;
                if((System.nanoTime()-startTimeall)/1000>600000000) break;
                Graph grf = vc.dupgraph(g);
                double t = rando.nextDouble();
                int mb=vc.remove(grf,t,startTimeall,timecut);
                if(F.size()<T.size()){
                    T=F;
                }
                long endTime1=System.nanoTime();
                long m = endTime1-startTime1;
                if(mb<newmb){
                    trace.println((double)(System.nanoTime()-startTime)/1000000000+","+mb);
                    System.out.println((double)(System.nanoTime()-startTime)/1000000000+","+mb);
                }
                newmb=Math.min(newmb,mb);
            }
            sol.println(newmb);
            sol.println(T);
            sol.close();
            trace.close();
            if(s==1) break;
        }
    }
    
    
    //initialize graph
    public Graph Readgraph(String dir) throws IOException{
        BufferedReader bufread = new BufferedReader(new FileReader(dir));
        String read = bufread.readLine();
        String[] sp = read.split(" ");
        int vertexnum =Integer.parseInt(sp[0]);
        Graph grf=new Graph(vertexnum);
        int i=0;
        while(i<vertexnum){
            read = bufread.readLine();
            i++;
            if(read.equals("")) continue;
            sp = read.split(" ");
            grf.addedge(sp,i);
        }
        return grf;
    }
    
    public Graph dupgraph(Graph g){
        Graph dupgraph=new Graph(g.edge);
        return dupgraph;
    }
    
    //Algorithm for HillClimb
    public int remove(Graph grf,double t,long startTimeall,double timecut){
        long startTimeR=System.nanoTime();
        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
        for(int j=1; j<=grf.edge.size(); j++){
            map.put(j, grf.edge.get(j).size());
        }
        List<Map.Entry<Integer, Integer>> mapList =
        new ArrayList<Map.Entry<Integer, Integer>>(map.entrySet());
        Collections.sort(mapList,new Comparator<Map.Entry<Integer, Integer>> (){
            @Override
            public int compare(Map.Entry<Integer, Integer> o1,
                               Map.Entry<Integer, Integer> o2) {
                return o1.getValue() - o2.getValue();
            }
        });
        
        List<Integer> Q=new LinkedList<Integer>();
        int p =0;
        Map.Entry<Integer, Integer> z = mapList.get(0);
        Q.add(0,z.getKey());
        for(int i=1;i<mapList.size();i++) {
            Map.Entry<Integer, Integer> x = mapList.get(i);
            Map.Entry<Integer, Integer> y = mapList.get(i-1);
            if(grf.edge.get(x.getKey()).size()==grf.edge.get(y.getKey()).size()){
                p++;
                int r=i-(int) (t*p);
                Q.add(r,x.getKey());			
                continue;
            }
            else{
                Q.add(i,x.getKey());
                p=0;
            }
        }
        
        
        int newmin=grf.edge.size();
        for(int i=0; i<grf.edge.size();i++){
            List<Integer> V=new LinkedList<Integer>(Q);
            HashSet<Integer> C=new HashSet<Integer>();
            C.add(V.get(i));
            V.remove(i);
            for(int it=0;it<V.size();it++){
                HashSet<Integer> D=new HashSet<Integer>(C);
                D.addAll(grf.edge.get(V.get(it)));
                if(D.size()<C.size()+grf.edge.get(V.get(it)).size()){
                    continue;
                }
                else{
                    C.add(V.get(it));
                    V.remove(it);		
                    it--;
                }
                int min=V.size();
                if(min<newmin){
                    F=V;
                }
                newmin=Math.min(min, newmin);
                if((System.nanoTime()-startTimeall)/1000000000>timecut){
                    s=1;
                    break;
                }
            }
            if(s==1) break;
            if((System.nanoTime()-startTimeR)/10>200000000) break;
        }
        return newmin;
    }
}
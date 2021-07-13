import java.util.*;

public class Solver {
    private double[][] segmentsGains;
    private Double[] deltas;
    private ArrayList<Double> FP_Gains;
    private ArrayList<ArrayList<Integer>> FPs;
    private ArrayList<boolean[]> FPMask;
    private ArrayList<Integer[]> NTLs;
    private ArrayList<Double> NTL_Gains;
    private ArrayList<ArrayList<Integer>> loops;
    private int numOfNodes;
    private ArrayList<boolean[]> loopsMask;
    private ArrayList<Double> loopGains;

    public Solver(double [][] segmentsGains){
        numOfNodes = segmentsGains.length;
        this.segmentsGains = segmentsGains;
        FPs = new ArrayList<ArrayList<Integer>>();
        FP_Gains = new ArrayList<Double>();
        FP_Gains = new ArrayList<Double>();
        loops = new ArrayList<ArrayList<Integer>>();
        FPMask = new ArrayList<boolean[]>();
        loopsMask = new ArrayList<boolean[]>();
        loopGains = new ArrayList<Double>();
        NTLs = new ArrayList<Integer[]>();
        NTL_Gains = new ArrayList<Double>();
        FPandLoops();
        deltas = new Double[FPs.size()];
        ArrayList<ArrayList<Integer>> labels = new ArrayList<ArrayList<Integer>>();
        for (int i = 0; i < loops.size(); i++) {
            labels.add(new ArrayList<Integer>());
            labels.get(labels.size() - 1).add(i);
        }
        generateNTL(labels, 1);

    }
    public String[] getFPs(){
        int counter = 0;
        String[] FPString = new String[FPs.size()];
        for(ArrayList<Integer> FP : FPs) {
            FPString[counter] = "";
            for (int i = 0; i < FP.size(); i++) { FPString[counter] += (FP.get(i) + 1) + " "; }
            counter++;
        }
        return FPString;
    }
    public Double[] getFPGains() { return FP_Gains.toArray(new Double[FP_Gains.size()]); }
    private void addToFP(ArrayList<Integer> arr) {
        FPs.add(arr);
        FPMask.add(mapNodes(arr));
        FP_Gains.add(Gainer(arr));
    }
    public String[] getLoops(){
        int counter = 0;
        String[] loopsString = new String[loops.size()];
        for(ArrayList<Integer> loop : loops){
            loopsString[counter] = "";
            for(int i = 0; i<loop.size(); i++){ loopsString[counter] += (loop.get(i)+1) + " "; }
            counter++;
        }
        return loopsString;
    }
    public Double[] getLoopGains() { return loopGains.toArray(new Double[loopGains.size()]); }
    private void addToLoops(ArrayList<Integer> loop) {
        loop.add(loop.get(0));
        if (!loopChecker(loop)) {
            loops.add(loop);
            loopsMask.add(mapNodes(loop));
            loopGains.add(Gainer(loop));
        }
    }
    public String[] getNTLs(){
    	String[] temp = getLoops();
		String nonString[] = new String[NTLs.size()];
		int itr = 0;
		for (Integer[] arr : NTLs) {
			nonString[itr] = "";

			if (arr.length > 0)
				nonString[itr] += temp[arr[0]];

			for (int i = 1; i < arr.length; i++)
				nonString[itr] += " , " + temp[arr[i]];

			itr++;
		}
		return nonString;
    }
    public Double[] getNTLGains() { return NTL_Gains.toArray(new Double[NTL_Gains.size()]); }
    private double Gainer(ArrayList<Integer> loop) {
        double gain = 1;
        if (loop.size() > 1) {
            for (int i = 0; i < loop.size() - 1; i++) gain *= segmentsGains[loop.get(i)][loop.get(i + 1)];return gain; }
        return segmentsGains[loop.get(0)][loop.get(0)];
    }
    private boolean[] mapNodes(ArrayList<Integer> flow) {
        boolean[] arr = new boolean[numOfNodes];
        for (int i = 0; i < flow.size(); i++) { arr[flow.get(i)] = true; }
        return arr;
    }
    private boolean isEquivalentLoop(boolean[] arr1, boolean[] arr2) {
        for (int i = 0; i < arr1.length; i++) { if (arr1[i] != arr2[i]) return false; }
        return true;
    }
    private boolean loopChecker(ArrayList<Integer> loop){
        boolean[] LP = mapNodes(loop);
        for(int i = 0; i<loops.size(); i++){
            if(loops.get(i).size() == loop.size() && isEquivalentLoop(LP,loopsMask.get(i))){ return true; } }
        return false;
    }

    private void DepthFirstSearch(ArrayList<Integer> path, boolean[] visited, int cursor) {
        path.add(cursor);
        visited[cursor] = true;
        if (path.size() > 1 && cursor == numOfNodes - 1) { addToFP(new ArrayList<Integer>(path));return; }
        for (int neighbour = 0; neighbour < numOfNodes; neighbour++) {
            if (segmentsGains[cursor][neighbour] != 0) {
                if (!visited[neighbour]) {
                    DepthFirstSearch(path, visited, neighbour);
                    path.remove(path.size() - 1);
                    visited[neighbour] = false;
                } else {
                    int index = path.indexOf(neighbour);
                    if (index != -1) {
                        List<Integer> temp = path.subList(index, path.size());
                        addToLoops(new ArrayList<Integer>(temp)); } } } }
    }

    private void FPandLoops() { DepthFirstSearch(new ArrayList<Integer>(), new boolean[numOfNodes], 0); }

    private boolean isNTL(ArrayList<Integer> loop) {
        int flag;
        for (int i = 0; i < numOfNodes; i++) {
            flag = 0;
            for (int j = 0; j < loop.size(); j++) { if (loopsMask.get(loop.get(j))[i]) flag++; }
            if (flag > 1) return false; }
        return true;
    }

    private double getNTLGain(ArrayList<Integer> arr) {
        double gain = 1;
        for (int j = 0; j < arr.size(); j++) gain *= loopGains.get(arr.get(j));
        return gain;
    }
    
    public Double[] getDeltas() {
        return deltas;
    }
    private void generateNTL(ArrayList<ArrayList<Integer>> arrList, int nth) {
        Set<List<Integer>> foundbefore = new HashSet<List<Integer>>();
        boolean moveOnFlag = false;
        ArrayList<ArrayList<Integer>> nextArrList = new ArrayList<ArrayList<Integer>>();
        for (int i = 0; i < arrList.size(); i++) {
            for (int j = i + 1; j < arrList.size(); j++) {
                for (int k = 0; k < arrList.get(j).size(); k++) {
                    int cand = arrList.get(j).get(k);
                    ArrayList<Integer> temp = new ArrayList<Integer>();
                    temp.addAll(arrList.get(i));
                    temp.add(cand);
                    if (isNTL(temp)) {
                        Collections.sort(temp);
                        if (!foundbefore.contains(temp)) {
                            foundbefore.add(temp);
                            moveOnFlag = true;
                            nextArrList.add(new ArrayList<Integer>());
                            nextArrList.get(nextArrList.size() - 1).addAll(temp);
                            NTLs.add(temp.toArray(new Integer[temp.size()]));
                            NTL_Gains.add(getNTLGain(temp));
                        }
                    }
                }
            }

        }
        if (moveOnFlag) {
            generateNTL(nextArrList, ++nth);
        }
    }
    public double getResult() {
        double current = 0;
        double delta = 0;
        double delta1 = 0;
        double delta2 = 0;
        double delta3 = 0;
        double delta4 = 0;

        int e = -1;
        for(int i = 0; i < loopGains.size();i++){
            delta1 += loopGains.get(i);
        }
        for (int i = 0; i < NTLs.size(); i++) {
            if (NTLs.get(i).length == 2) { delta2 += NTL_Gains.get(i); }
        }
        for (int i = 0; i < NTLs.size(); i++) {
            if (NTLs.get(i).length == 3) { delta3 += NTL_Gains.get(i); }
        }
        for (int i = 0; i < NTLs.size(); i++) {
            if (NTLs.get(i).length == 4) { delta4 += NTL_Gains.get(i); }
        }
        delta = 1 - delta1 + delta2 -delta3 + delta4;
        double nominatorTerm = 0;
        double deltaN;
        double deltaN1 = 0;
        double deltaN2 = 0;
        double deltaN3 = 0;
        double deltaN4 = 0;
        for (int i = 0; i < FPs.size(); i++) {
            deltaN = 1;
            deltaN1 = 0;
            deltaN2 = 0;
            deltaN3 = 0;
            deltaN4 = 0;
            current = 0;
            e = -1;
            for (int j = 0; j<loops.size();j++){
                if (isLoopWithFP(new ArrayList<Integer>(loops.get(j)), i)) {
                    deltaN1 += loopGains.get(j);
                }
            }
            for (int j = 0; j < NTLs.size(); j++) {
                if (isNTLWithFP(new ArrayList<Integer>(Arrays.asList(NTLs.get(j))), i)) {
                    for (int k = 0; k < NTLs.size(); k++) {
                        if (NTLs.get(k).length == 2) { deltaN2 += NTL_Gains.get(k); }
                    }
                    for (int k = 0; k < NTLs.size(); k++) {
                        if (NTLs.get(k).length == 3) { deltaN3 += NTL_Gains.get(k); }
                    }
                    for (int k = 0; k < NTLs.size(); k++) {
                        if (NTLs.get(k).length == 4) { deltaN4 += NTL_Gains.get(k); }
                    }
                } else
                    break;
            }
            deltaN = 1 - deltaN1 + deltaN2 - deltaN3 + deltaN4;
            deltas[i] = deltaN;
            nominatorTerm += deltaN * FP_Gains.get(i);
        }
        return nominatorTerm / delta;
    }
    private boolean isLoopWithFP(ArrayList<Integer> loop,int index){
        ArrayList<Integer> p = new ArrayList<Integer>();
        p = FPs.get(index);
        for(int i = 0; i< p.size(); i++){
            for (int j = 0;j<loop.size(); j++){if(p.get(i).equals(loop.get(j)) ){return false;}}
        }
        return true;
    }
    
    private boolean isNTLWithFP(ArrayList<Integer> loop, int p) {
        int flag;
        for (int i = 0; i < numOfNodes; i++) {
            flag = 0;
            for (int j = 0; j < loop.size(); j++) { if (loopsMask.get(loop.get(j))[i]) flag++; }
            if (FPMask.get(p)[i])
                flag++;
            if (flag > 1)
                return false;
        }
        return true;
    }
}

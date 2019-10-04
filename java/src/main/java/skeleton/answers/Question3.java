package skeleton.answers;

public class Question3 {

    public static int whereDidIFinish(int[] scores, int[] alice) {
      
        TreeSet<Integer> ts=new TreeSet<Integer>();
        TreeMap<Integer, Integer> tm=new TreeMap<Integer, Integer>();
        int max=Integer.MIN_VALUE;
        int idx=-1;
        for(int i=0;i<scores.length;i++)
        {
            ts.add(scores[i]);
        }
        for(int i=0;i<alice.length;i++)
        {
           ts.add(alice[i]);
        }
        int k=ts.size();
        for(Integer i:ts)
        {
            tm.put(i, k);
            k--;
        }
        for(int i=0;i< alice.length;i++)
        {
            alice[i]=tm.get(alice[i]);
        }
        tm.clear();
            for (Integer i : alice) {
                Integer j = tm.get(i);
                tm.put(i, (j == null) ? 1 : j + 1);
            }
            for (Map.Entry<Integer, Integer> m:tm.entrySet())
            {
                if(m.getValue()>max) {
                    max = m.getValue();
                    idx=m.getKey();
                }
            }

        return idx;
    }
}

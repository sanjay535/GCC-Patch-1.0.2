package skeleton.answers;

public class Question2 {

    public static int riskAndReward(int[] risk, int[] bonus, int[] trader) {
        int sum=0;

        for(int i=0;i<trader.length;i++)
        {
            int max=Integer.MIN_VALUE;
           for(int j=0;j<risk.length;j++)
           {
               if(trader[i]>=risk[j])
               {
                 if(bonus[j]>max)
                     max=bonus[j];

               }
           }
           sum+=max;
        }

        return sum;
    }
}

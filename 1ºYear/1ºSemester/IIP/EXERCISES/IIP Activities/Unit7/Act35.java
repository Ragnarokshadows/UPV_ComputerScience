package Unit7;

public class Act35{
    public class CashMachine{
        private int [] amount;
        private double [] values = {0.01,0.02,0.05,0.1,0.2,0.5,1,2,5,10,20,50,
        100,200,500};
        
        public CashMachine(){
            amount = new int [values.length];
            int i;
            
            for (i = 0;i < amount.length;i++){
                if (values[i] <= 50){
                    amount[i] = 5;
                }
                else{
                    amount[i] = 0;
                }
            }
        }
        public double [] getValues(){return values;}
        public int[] change(int [] namount, double apaid){
            int i,j;
            double sum1 = 0;
            double sum2 = apaid;
            int [] res = new int [namount.length];
            
            for (i = 0;i < namount.length;i++){
                    res[i] = 0;
                }
            for (i = 0;i < namount.length;i++){
                sum1 = sum1 + (namount[i] * values[i]);
                amount[i] = amount[i] + namount[i];
            }
            if (apaid - sum1 < 0){
                for (j =  amount.length - 1;j >= 0;j--){
                    while (values[j] <= sum2 && amount[j] > 0 &&
                    sum2 - values[j] >= 0){
                        sum2 = sum2 - values[j];
                        amount[j] = amount[j] - 1;
                    } 
                }
                if (sum2 > 0){
                    res = namount;
                }
            }
            
            return res;
        }
    }
}
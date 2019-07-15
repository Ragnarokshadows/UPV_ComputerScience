package Unit7;

public class Act49{
    public static double sumSubMat(double[][] m, int r, int c){
        int i, j;
        double sum = 0;
        
        for (i = r - 1;i <= r + 1;i++){
            for (j = c - 1;j <= c + 1;j++){
                if (i >= 0 && i < m.length && j >= 0 && j < m[0].length){
                    sum = sum + m[i][j];
                }
            }
        }
        
        return sum;
    }
}
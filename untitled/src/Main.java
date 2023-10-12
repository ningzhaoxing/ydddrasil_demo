import java.util.Scanner;
public class Main {
    static int mod = (int) 1e9 + 7;
    static int N=(int) 2e5+7;
    public static void main(String[] args) {
        Scanner sc= new Scanner(System.in);
        String s=sc.nextLine();

        int n = s.length();
        long[] num= new long[N];//记录！的数目
        //计算后缀！的数量
        for(int i = n-1; i >= 0; i--){
            num[i] = num[i + 1];
            if(s.charAt(i) == '!') num[i] ++;
        }
        long ans=0l;
        long[] B=new long[N];
        long[] BO=new long[N];
        long[] BOB=new long[N];
        long[] BOBG=new long[N];
        long[] BOBGO=new long[N];
        long[] BOBGOO=new long[N];
        long[] BOBGOOD=new long[N];
        for (int i = 0; i < n; i++) {
            //动态更新
            if(i!=0) {
                B[i] = B[i - 1];
                BO[i] = BO[i - 1];
                BOB[i] = BOB[i - 1];
                BOBG[i] = BOBG[i - 1];
                BOBGO[i] = BOBGO[i - 1];
                BOBGOO[i] = BOBGOO[i - 1];
                BOBGOOD[i] = BOBGOOD[i - 1];
            }
            if(s.charAt(i)=='B'){
                B[i]++;
                B[i]%=mod;
                BOB[i]+=BO[i];
                BOB[i]%=mod;
            }
            if(s.charAt(i)=='O'){
                BO[i]+=B[i];
                BO[i]%=mod;

                BOBGOO[i]+=BOBGO[i];
                BOBGOO[i]%=mod;
                //先添加BOBGOO序列数
                BOBGO[i]+=BOBG[i];
                BOBGO[i]%=mod;
            }
            if(s.charAt(i)=='G'){
                BOBG[i]+=BOB[i];
                BOBG[i]%=mod;
            }
            if(s.charAt(i)=='D'){
                BOBGOOD[i]+=BOBGOO[i];
                BOBGOOD[i]%=mod;
                if(BOBGOOD[i]!=0) {
                    ans =(ans+(BOBGOO[i] *(lsm(2, num[i]) - 1))%mod)%mod;
                }
            }
        }
        System.out.println((int)ans);
    }
    public static long lsm(long a,long b){
        long ans=1;
        while(b>0){
            if(b%2==1) ans=ans*a%mod;
            a=a*a%mod;
            b/=2;
        }
        return ans;
    }
}
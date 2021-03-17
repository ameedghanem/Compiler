#include<bits/stdc++.h> 
using namespace std; 
using ll = long long ;

ll P  = 1000000007;
  
/* Iterative Function to calculate (x^y)%p 
  in O(log y) */
int power(int x, int y, int p) 
{ 
    int res = 1;      // Initialize result 
  
    x = x % p;  // Update x if it is more than or 
                // equal to p 
  
    while (y > 0) 
    { 
        // If y is odd, multiply x with result 
        if (y & 1) 
            res = (res*x) % p; 
  
        // y must be even now 
        y = y>>1; // y = y/2 
        x = (x*x) % p; 
    } 
    return res; 
} 
  
// Returns n^(-1) mod p 
int modInverse(int n, int p) 
{ 
    return power(n, p-2, p); 
} 
  
// Returns nCr % p using Fermat's little 
// theorem. 
int nCrModPFermat(int n, int r, int p) 
{ 
   // Base case 
   if (r==0) 
      return 1; 
  
    int fac[n+1]; 
    fac[0] = 1; 
    for (int i=1 ; i<=n; i++) 
        fac[i] = fac[i-1]*i%p; 
  
    return (fac[n]* modInverse(fac[r], p) % p * 
            modInverse(fac[n-r], p) % p) % p; 
}

  
// Driver program 
int main() 
{ 
    // p must be a prime greater than n. 
    int T, N;
    string A, B;
    cin >> T >> N;
    cin >> A >> B;
    while(T--){
        int count_zero_A = 0, count_ones_A = 0, count_zero_B = 0, count_ones_B = 0;

        for (char c:A) {
            if (c == '1')
                count_ones_A += 1;
     
            else if (c == '0')
                count_zero_A += 1;
        }
     
        for (char c:B){
            if (c == '1')
                count_ones_B += 1;
            else if (c == '0')
                count_zero_B += 1;
        }

        int max1 = min(count_zero_A, count_ones_B) + min(count_ones_A, count_zero_B);
        int min1 = N - min(count_zero_B, count_zero_A) - min(count_ones_A, count_ones_B);

        ll sum=0;

        if(min1 > max1)
            swap(min1, max1);


        for(ll i = min1-1; i<= max1 && (i-min1+1)%2 == 0 ; i++ ){
            sum = (sum + nCrModPFermat(N, i, P) % P) % P;
        }

        cout << sum << endl;
    }
    return 0; 
}
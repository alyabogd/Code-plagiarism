#include<bits/stdc++.h>
using namespace std;

#define sin(x) scanf("%d",&x)
#define sin2(x,y) scanf("%d%d",&x,&y)
#define sin3(x,y,z) scanf("%d%d%d",&x,&y,&z)

#define pb push_back
#define mp make_pair
#define y1 asdnqw
#define next mdamdamda
#define right praviy
#define x first
#define y second
#define endl "\n"
#define int long long
const int N=2000001;
const int B1=31,B2=27;
const int md1=1e9+7,md2=1e9+13;
const int md=1e9+7;
string s;
int n,m,p1[N],p2[N],h1[N],h2[N],l1,l2,r1,r2,ans,csum,ss[N],b[N];
int pl,q;
// comments
void fail() {
    cout<<0;
    /*

    */
    exit(0);
}

int main() {
    // don't
    cin.tie(0);ios_base::sync_with_stdio(0);
    cin>>n>>m;
    cin>>s;
    // bother
    pl=s.size();
    s='#'+s;
    p1[0]=p2[0]=1;
    // me

    for(int i=1;i<=n;++i){
    p2[i]=(p2[i-1]*B2)%md2;
        p1[i]=(p1[i-1]*B1)%md1;
    }
    for(int i=1;i<s.size();++i){
        h2[i]=(h2[i-1]+(s[i]-'a'+1)*p2[i])%md2;
        h1[i]=(h1[i-1]+(s[i]-'a'+1)*p1[i])%md1;
    }
    for(int i=1;i<=m;++i){
        cin>>b[i];
        ss[b[i]]++;
        // at all
        ss[b[i]+pl]--;
    }
    for(int i=2;i<=m;++i){
        if(b[i-1]+pl)-b[i]<=0)continue;
                l1=(h1[q]-p1[pl-q])%md1;
                // comments
                l2=(h2[q]-p2[pl-q])%md2;
                r1=(h1[pl]*h1[pl-q]+md1);
                /* more more more*/
                if(r1>=md1)r1-=md1;
                        r2=(h2[pl]*h2[pl-q]+md2);if(r2>=md2)r2-=md2;
        // cout<<l1<<' '<<r1<<' '<<l2<<' '<<r2<<' '<<q<<endl;
        if(l1!=r1||l2!=r2)fail();
    }
    ans=1;
    for(int i=1;i<=n;++i){
        /*
        their
        kinds
        */
        csum+=ss[i];
        if(csum==0)ans=(ans*26)%md;
    }
    cout<<ans;
}

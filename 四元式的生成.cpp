#include<iostream>
#include<string>
int n=301, ss=0;
using namespace std;
void zh(int n)
{
    if(n>=300)
        cout<<"T"<<n-300;
    else
        cout<<char(n);
}
string delet(string s)
{
    string str=s;
    string::iterator it;
    for (it =str.begin(); it != str.end(); ++it)
    {
        if ( *it == ':')
        {
            str.erase(it);
        }
    }
 return str;

}

int p(char n)
{
    if(n=='=')
        return 0;
    else if(n==')')
        return 10;
    else if(n=='+'||n=='-')
        return 20;
    else if(n=='*'||n=='/')
        return 30;
    else if(n=='(')
        return 40;
    else
        return -1;
}
int main()
{
    string s;
    cin>>s;
    s=delet(s);
    char a[100];
    int b[100];
    int atop=0;
    int btop=0;
    for(int i=0;i<s.length()-1;i++)
    {
        /**********************************/
        if(p(s[i])==-1)
            b[btop++]=s[i];
        /**********************************/
        else if(atop>1)
            {
                if(s[i]=='('&&s[i+1]=='-')//“（”后边有“-”存在的情况
                {

         cout<<"T"<<n-300<<":=-";zh(s[i+2]);
        cout<<"     ( "<<"uminus,";zh(s[i+2]);cout<<",_,"<<"T"<<n-300<<")"<<endl;
                    i=i+3;
                    b[btop++]=n;
                    n++;
                    ss++;
                }
                else if(s[i]=='('&&s[i+1]!='-')//“（”后面不是“—”
                {
                    a[atop++]=s[i];
                }
                else if(s[i]==')'&& p(s[i])<=p(a[atop-1]))//遇到“）”的情况
                {
                    while(a[atop-1]!='(')//直到“（”出栈
                    {
        cout<<"T"<<n-300<<":=";zh(b[btop-2]);cout<<a[atop-1];zh(b[btop-1]);
        cout<<"     ("<<a[--atop]<<",";zh(b[btop-2]);cout<<",";zh(b[btop-1]);cout<<","<<"T"<<n-300<<")"<<endl;
                        btop=btop-2;
                        b[btop++]=n;
                        n++;
                        ss++;
                    }
                    if(a[atop-1]=='(')
                        --atop;
                }
                else if(s[i]!=')'&&p(s[i])<=p(a[atop-1]))//不是“）”
                {
                    while(p(s[i])<=p(a[atop-1])&&a[atop-1]!='='&&a[atop-1]!='(')
                    {
        cout<<"T"<<n-300<<":=";zh(b[btop-2]);cout<<a[atop-1];zh(b[btop-1]);
        cout<<"     ("<<a[--atop]<<",";zh(b[btop-2]);cout<<",";zh(b[btop-1]);cout<<","<<"T"<<n-300<<")"<<endl;
                        btop=btop-2;
                        b[btop++]=n;
                        n++;
                        ss++;
                    }
                    a[atop++]=s[i];
                }
                else
                {
                    a[atop++]=s[i];
                }
        }
        /****************************/
        else
        {
            a[atop++]=s[i];
        }
    }

if(s[s.length()-1]==')')
        {
            while(p(s[s.length()-1])<=p(a[atop-1])&&a[atop-1]!='(')
            {
        cout<<"T"<<n-300<<":=";zh(b[btop-2]);cout<<a[atop-1];zh(b[btop-1]);
        cout<<"     ("<<a[--atop]<<",";zh(b[btop-2]);cout<<",";zh(b[btop-1]);cout<<","<<"T"<<n-300<<")"<<endl;
                btop=btop-2;
                b[btop++]=n;
                n++;
                ss++;
            }
            if(a[atop-1]=='(')
                --atop;
                while(a[atop-1]!='=')
                {
                    {
        cout<<"T"<<n-300<<":=";zh(b[btop-2]);cout<<a[atop-1];zh(b[btop-1]);
        cout<<"     ("<<a[--atop]<<",";zh(b[btop-2]);cout<<",";zh(b[btop-1]);cout<<","<<"T"<<n-300<<")"<<endl;
                    btop=btop-2;
                    b[btop++]=n;
                    n++;
                    ss++;
                    }
                }
        }
    else
        {
            b[btop++]=s[s.length()-1];
        cout<<"T"<<n-300<<":=";zh(b[btop-2]);cout<<a[atop-1];zh(b[btop-1]);
        cout<<"     ("<<a[--atop]<<",";zh(b[btop-2]);cout<<",";zh(b[btop-1]);cout<<","<<"T"<<n-300<<")"<<endl;
            btop=btop-2;
            b[btop++]=n;
            n++;
            ss++;
        }
        zh(b[btop-2]);cout<<":=";zh(b[btop-1]);
        cout<<"     (:=,";zh(b[--btop]);cout<<",-,";zh(b[--btop]);cout<<")"<<endl;
        return 0;
}
//Y=a+b*(c+(-d)*(e+f))
//Y=(a+b*(c-d*e/f+g*(h-i*x*(j+k*(-l)*(j+k)))+w))
//H=(d+a-(c+b-q)*a)+c-(a+b*(c+d))



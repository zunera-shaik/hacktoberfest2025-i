#include<iostream>
using namespace std;
int main()
{
    int arr[50],n,i;
    cout<<"Enter the number of elements: ";
    cin>>n;
    cout<<"Enter "<<n<<" elements\n";
    for(i=0;i<n;i++)
        cin>>arr[i];
    cout<<"Entered elements\n";
    for(i=0;i<n;i++)
        cout<<arr[i]<<" ";
    int start=0,end=n-1;
    while(start<end)
    {
        int temp=arr[start];
        arr[start]=arr[end];
        arr[end]=temp;
        start++;
        end--;
    }
    cout<<"Reversed elements\n";
    for(i=0;i<n;i++)
        cout<<arr[i]<<" ";
    return 0;
}

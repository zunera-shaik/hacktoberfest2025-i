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
    int index=0;
    for(i=0;i<n;i++)
    {
        if(arr[i]!=0)
        {
            arr[index]=arr[i];
            index++;
        }
    }
    while(index<n)
    {
        arr[index]=0;
        index++;
    }
    cout<<"After moving 0s, elements\n";
    for(i=0;i<n;i++)
        cout<<arr[i]<<" ";
    return 0;
}

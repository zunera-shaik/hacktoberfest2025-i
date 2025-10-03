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
    int index=1;
    for(i=1;i<n;i++)
    {
        if(arr[i]!=arr[index-1])
        {
            arr[index]=arr[i];
            index++;
        }
    }
    cout<<"After moving duplicates, elements\n";
    for(i=0;i<index;i++)
        cout<<arr[i]<<" ";
    return 0;
}

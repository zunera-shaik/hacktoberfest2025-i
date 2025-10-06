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
    int min=arr[0],max=arr[0];
    for(i=1;i<n;i++)
    {
        if(arr[i]>max)
            max=arr[i];
        if(arr[i]<min)
            min=arr[i];
    }
    cout<<"Maximum element is "<<max<<" and minimum element is "<<min;
    return 0;
}

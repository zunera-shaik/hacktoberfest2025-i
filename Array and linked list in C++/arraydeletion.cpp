#include<iostream>
using namespace std;
int main()
{
    int arr[50],i;
    cout<<"Enter the number of elements: ";
    int n;
    cin>>n;
    cout<<"Enter "<<n<<" elements\n";
    for(i=0;i<n;i++)
        cin>>arr[i];
    cout<<"Entered elements\n";
    for(i=0;i<n;i++)
        cout<<arr[i]<<" ";
    cout<<"\nDeletion at end\n";
    n--;
    cout<<"Array elements\n";
    for(i=0;i<n;i++)
        cout<<arr[i]<<" ";
    cout<<"\nDeletion at beginning\n";
    for(i=0;i<n-1;i++)
        arr[i]=arr[i+1];
    n--;
    cout<<"Array elements\n";
    for(i=0;i<n;i++)
        cout<<arr[i]<<" ";
    cout<<"\nDeletion at index\n";
    cout<<"Enter the index: ";
    int index;
    cin>>index;
    for(i=index;i<n-1;i++)
        arr[i]=arr[i+1];
    n--;
    cout<<"Array elements\n";
    for(i=0;i<n;i++)
        cout<<arr[i]<<" ";
    return 0;
}

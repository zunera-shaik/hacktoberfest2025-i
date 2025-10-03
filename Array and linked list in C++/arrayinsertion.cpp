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
    cout<<"\nInsertion at end\n";
    cout<<"Enter the element to enter at the end: ";
    int end;
    cin>>end;
    arr[n]=end;
    n++;
    cout<<"Array elements\n";
    for(i=0;i<n;i++)
        cout<<arr[i]<<" ";
    cout<<"\nInsertion at beginning\n";
    cout<<"Enter the element to enter at the beginning: ";
    int beg;
    cin>>beg;
    for(i=n;i>0;i--)
        arr[i]=arr[i-1];
    arr[0]=beg;
    n++;
    cout<<"Array elements\n";
    for(i=0;i<n;i++)
        cout<<arr[i]<<" ";
    cout<<"\nInsertion at index\n";
    cout<<"Enter the index: ";
    int index;
    cin>>index;
    cout<<"Enter the element to enter at the index "<<index<<": ";
    int el;
    cin>>el;
    for(i=n;i>index;i--)
        arr[i]=arr[i-1];
    arr[index]=el;
    n++;
    cout<<"Array elements\n";
    for(i=0;i<n;i++)
        cout<<arr[i]<<" ";
    return 0;
}

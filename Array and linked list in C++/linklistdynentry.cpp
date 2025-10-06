#include<iostream>
using namespace std;
struct Node
{
    int data;
    Node *next;
};
int main()
{
    int n,val;
    Node *head= NULL;
    Node *temp= NULL;
    cout<<"Enter the number of nodes: ";
    cin>>n;
    for(int i=0;i<n;i++)
    {
        cout<<"Enter data for node "<<i+1<<": ";
        cin>>val;
        //create node
        Node *node= new Node();
        node->data= val;
        node->next = NULL;
        if(head==NULL)
        {
            head=node;
            temp=head;
        }
        else{
            temp->next=node;
            temp=temp->next;
        }
    }
    //traverse
    cout<<"\nLinked list\n";
    temp=head;
    while(temp!=NULL)
    {
        cout<<temp->data<<" ";
        temp=temp->next;
    }
    return 0;
}

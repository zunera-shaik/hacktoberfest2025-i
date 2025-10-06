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
    cout<<"\nInsertion at beginning\n";
    int val1;
    cout<<"Enter value to insert at beginning: ";
    cin>>val1;
    Node *beg=new Node();
    beg->data=val1;
    beg->next=head;
    head= beg;
    cout<<"\nLinked list\n";
    temp=head;
    while(temp!=NULL)
    {
        cout<<temp->data<<" ";
        temp=temp->next;
    }
    cout<<"\nInsertion at end\n";
    int val2;
    cout<<"Enter value to insert at end: ";
    cin>>val2;
    Node *end=new Node();
    end->data=val2;
    end->next=NULL;
    if(head==NULL)
        head=end;
    else{
        Node *temp=head;
        while(temp->next!=NULL)
            temp=temp->next;
        temp->next=end;
    }
    cout<<"\nLinked list\n";
    temp=head;
    while(temp!=NULL)
    {
        cout<<temp->data<<" ";
        temp=temp->next;
    }
    cout<<"\nInsertion at a specific position\n";
    int pos,val3;
    cout<<"Enter position: ";
    cin>>pos;
    cout<<"Enter value: ";
    cin>>val3;
    Node *mid=new Node();
    mid->data=val3;
    if(pos==0)
    {
        mid->next=head;
        head=mid;
    }
    else
    {
        temp=head;
        for(int i=0;i<pos-1 && temp!=NULL;i++)
            temp=temp->next;
        if(temp==NULL)
            cout<<"Invalid position";
        else{
            mid->next=temp->next;
            temp->next=mid;
        }
    }
    cout<<"\nLinked list\n";
    temp=head;
    while(temp!=NULL)
    {
        cout<<temp->data<<" ";
        temp=temp->next;
    }
    return 0;
}

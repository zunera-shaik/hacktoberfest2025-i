#include<iostream>
using namespace std;
struct Node
{
    int data;
    Node *next;
};
int main()
{
    Node *head=NULL;
    Node *temp=NULL;
    int n,value;
    cout<<"Enter the number of nodes: ";
    cin>>n;
    for(int i=0;i<n;i++)
    {
        cout<<"Enter data for node "<<i+1<<": ";
        cin>>value;
        Node *node=new Node();
        node->data=value;
        node->next=NULL;
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
    cout<<"\nEntered nodes\n";
    temp=head;
    while(temp!=NULL)
    {
        cout<<temp->data<<" ";
        temp=temp->next;
    }
    //Delete at beginning
    if(head!=NULL)
    {
        Node *del=head;
        head=head->next;
        delete del;
    }
    cout<<"\nLinked list\n";
    temp=head;
    while(temp!=NULL)
    {
        cout<<temp->data<<" ";
        temp=temp->next;
    }
    //Delete at end
    if(head!=NULL && head->next==NULL)
    {
        delete head;
        head=NULL;
    }
    else{
        temp=head;
        while(temp->next->next!=NULL)
            temp=temp->next;
        delete temp->next;
        temp->next=NULL;
    }
    cout<<"\nLinked list\n";
    temp=head;
    while(temp!=NULL)
    {
        cout<<temp->data<<" ";
        temp=temp->next;
    }
    //Deletion at any position
    int pos;
    cout<<"\nEnter position 0 based: ";
    cin>>pos;
    if(head==NULL)
        cout<<"List is empty";
    else if(pos==0)
    {
        Node *del=head;
        head=head->next;
        delete head;
    }
    else{
        temp=head;
        for(int i=0;i<pos-1 && temp->next!=NULL;i++)
        {
            temp=temp->next;
        }
        if(temp->next==NULL)
            cout<<"Invalid position";
        else{
            Node *del=temp->next;
            temp->next=temp->next->next;
            delete del;
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

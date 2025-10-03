#include<iostream>
using namespace std;
struct Node
{
    int data;
    Node *next;
};
int main()
{
    //create nodes
    Node *head=new Node();
    Node *second=new Node();
    Node *third=new Node();

    //assign values and connect nodes
    head->data=10;
    head->next=second;
    second->data=20;
    second->next=third;
    third->data=30;
    third->next=NULL;

    //traverse
    Node *temp=head;
    cout<<"Linked list\n";
    while(temp!=NULL)
    {
        cout<<temp->data<<" ";
        temp=temp->next;
    }
    return 0;
}

#include <iostream>
#include <cstdio>
#include <stdlib.h>
#include <cstring>
#include <malloc.h>
#include <ctime>
#include "windows.h"
using namespace std;
#define LEN sizeof(node)

struct employee
{
	int num;
	char name[10];
	int age;
	int price;
	int saraly;
	struct employee *next;
};
int n;               //录入员工人数

typedef struct employee node;

node* creat()
{
    node *ptr,*head,*newnode;
    head=(node*)malloc(LEN);
    ptr=head;
    ptr->next=NULL;
    cout<<"请输入要录入员工人数:";
    cin>>n;
    do{
        cout<<"请输入员工的编号，姓名，年龄，基本工资，工资：";
        cin>>ptr->num>>ptr->name>>ptr->age>>ptr->price>>ptr->saraly;
        newnode=(node*)malloc(LEN);
        ptr->next=newnode;
        newnode->next=NULL;
        ptr=ptr->next;
        n--;
    }while(n!=0);
    cout<<"录入员工信息已完成！"<<endl;
    return head;
}


node* Insert_(node * &head)
{
    cout<<"请输入要插入职工信息(编号，姓名，年龄，基本工资，工资)：";
    node* ptr,*temp,*newnode;
    ptr=head;
    newnode=(node*)malloc(LEN);
    cin>>newnode->num>>newnode->name>>newnode->age>>newnode->price>>newnode->saraly;
    int n=newnode->num;
    if(head==NULL)
    {
        head=newnode;
        newnode->next=NULL;
        return head;
    }

    while(n>ptr->num && ptr->next!=NULL)
    {
        temp=ptr;
        ptr=ptr->next;
    }
    if((ptr==head&&head->next!=NULL) || n<=head->num)
    {
        newnode->next=head;
        return newnode;
    }
    if(n>ptr->num&&ptr->next==NULL)
    {
         ptr->next=newnode;
        newnode->next=NULL;
        return head;
    }
    if(n<=ptr->num)
    {
        temp->next=newnode;
        newnode->next=ptr;
        return   head;
    }
}
void change(node *&head)
{
    node *ptr=head;
    int num,age,price,saraly;
    char name[10],sex[10];
    cout<<"请输入要修改信息的员工编号:";
    cin>>num;
    cout<<"请输入更改后的信息:";
    cin>>name>>age>>price>>saraly;
    while(ptr->next!=NULL){
        if(ptr->num==num)
            {strcpy(ptr->name,name);
             ptr->age=age;
             ptr->price;
            ptr->saraly=saraly;
            break;
            }
        ptr=ptr->next;
    }
}

void  del_ptr(node* &head)
{
    int num;
    cout<<"请输入要删除的员工编号:";
	cin>>num;
	node* temp,*ptr;
	ptr = head;
	if (head == NULL)
        cout<<"链表为空!\n";
	else
	{
		ptr = head;
		while( ptr->num!=num  && ptr->next!=NULL )
		{
			temp = ptr;
			ptr = ptr->next;
		}
		if( ptr->num==num )
		{
			if (ptr == head)
			{
				head = head->next;
				free(ptr);
			}
			else
			{
				temp->next = ptr->next;
                printf("已删除第 %d 号员工 姓名：%s 年龄：%d 基本工资：%d 薪资:%d\n",ptr->num,ptr->name,ptr->age,ptr->price,ptr->saraly);
				free(ptr );
			}
		}
		else
			cout<<"无此员工编号\n";
	}

}
void find_ptr(node* &head)
{
    int findword;
    cout<<"请输入要查找的员工的编号：";
    cin>>findword;
    node* ptr=head;
	int	find=0;
			while (ptr!=NULL)
			{
				if(ptr->num==findword)
				{
					find++;
					break;
				}
				ptr=ptr->next;
			}
			if(find==0)
                printf("没有找到该员工相关信息\n");
			else
                {cout<<"输出员工信息:"<<endl;
            printf("\t[%2d]\t[ %-10s]\t[%2d][%3d]\t[%3d]\n",ptr->num,ptr->name,ptr->age,ptr->price,ptr->saraly);
        }
}
void print(node* &head)
{
    node* ptr=head;
    if(head==NULL){cout<<"头指针为空！"<<endl;return;}
    printf("打印全部员工信息！");
    printf("\n\n\t员工编号    姓名\t年龄\t基本工资\t工资\n");          /*打印剩余链表数据*/
	printf("\t====================================================\n");
	while(ptr!=NULL)
	{printf("\t[%2d]\t[ %-10s]\t[%2d]\t[%3d]\t\t[%3d]\n",ptr->num,ptr->name,ptr->age,ptr->price,ptr->saraly);
		ptr=ptr->next;
	}
	printf("\t====================================================\n");
}
void time(node* &head,int Year)
{
    time_t rawtime;
    time ( &rawtime );
    cout<<"当前时间: "<<ctime(&rawtime)<<endl;
    node*ptr=head;
   int year;
   SYSTEMTIME ct;
   GetLocalTime(&ct);//如果用GetSystemTime(&ct);那么获取的是格林尼治标准时间
   year=ct.wYear;
   int n=year-Year;
    if(n){
        while(ptr!=NULL){ptr->age=ptr->age+n;
        ptr=ptr->next;
    }
    }
    if(n!=2)return;
    cout<<"已经经过两年后的工资为："<<endl;

    while(ptr!=NULL)
    {
        ptr->price=ptr->price+30;
        ptr->saraly=ptr->saraly+30;
        printf("\t[%2d]\t[ %-10s]\t[%2d]\t[%3d]\t\t[%3d]\n",ptr->num,ptr->name,ptr->age,ptr->price,ptr->saraly);
        ptr=ptr->next;
    }
    print(head);
}
void destroynode(node* &head)
{
    node* p=head;
    while(p!=NULL){
        node* ptr=p;
        p=p->next;
        free(ptr);
    }
    cout<<"链表已销毁！"<<endl;
}
void sort(node*&head) //单链表排序
{
    node *p,*q,*r;
    int temp;
    for(p=head->next;p!=NULL;p=p->next)
    {
        r=p;
        for(q=p->next;q!=NULL;q=q->next)
            if(q->num<r->num) r=q;
                temp=p->num;
                p->num=r->num;
                r->num=temp;
    }
}
int main()
{
    node *head;
    head=creat();
    sort(head);
    print(head);
}

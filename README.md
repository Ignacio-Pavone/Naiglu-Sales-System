NAIGLÚ SYSTEM USER GUIDE

Setting up:

 After cloning the Github repository, which is available at https://github.com/Ignacio-Pavone/Naiglu-Sales-System,the program must be compiled. For this, we are going to use IntelliJ IDEA Community. Here’s the step by step guide.

1. Go to File, and click on Open Project.

![image](https://user-images.githubusercontent.com/84025828/172191665-895a0aef-c167-46a0-8f81-41f60d395e89.png)


If you are at this window, you should click on open aswell.

![image](https://user-images.githubusercontent.com/84025828/172191728-bef0f0db-efaa-4993-985c-f8d3ff0209e3.png)

Click on the project folder (in this case, it’s named Proyecto-Final-Java)

![image](https://user-images.githubusercontent.com/84025828/172191786-69b672c9-e9e3-4204-8b64-70fc37815976.png)

Once opened, go to File->Project Structure.

![image](https://user-images.githubusercontent.com/84025828/172191835-63390621-fcc7-4cca-bead-8673d90a1a21.png)

Click on artifacts, and then on the plus sign.

![image](https://user-images.githubusercontent.com/84025828/172191888-4abd54c0-5130-481d-b1fb-c15b899d44cc.png)

Select “JAR->From modules with dependencies…”

![image](https://user-images.githubusercontent.com/84025828/172191944-54d097c7-3830-4bac-aee9-e0ec569027fe.png)


Click on the folder for “Main class”, select the Main file, and then press OK

![image](https://user-images.githubusercontent.com/84025828/172192059-1003b85b-8393-4aea-bbae-08171062f2d5.png)

Press OK and close the Project Structure dialog.
Press the “Build” button. On the red folder “out”, go to artifacts->(folder name)->name.jar

![image](https://user-images.githubusercontent.com/84025828/172192180-20b944ad-bdbd-4ba5-a872-33b82dc54c8d.png)

![image](https://user-images.githubusercontent.com/84025828/172192204-339bd782-c92a-4713-9d01-ad496bfa8090.png)

It 's done! Now all you have to do is move the .jar file to a new folder.

![image](https://user-images.githubusercontent.com/84025828/172192251-23109530-4af5-4d54-a488-9b196e6d80b0.png)


USING NAIGLÚ:

When logging in with Naiglu, you will be welcomed with a login screen. This screen requires a SQL connection. We recommend using XAMPP for a localhost connection for the database.

![image](https://user-images.githubusercontent.com/84025828/172192398-03b20de6-8957-49a6-a8c5-cb04b076f26e.png)

Here's a SQL file loaded with users. You might wish to change them, since you need to adjust this for the users in your company. You must keep in mind, though, there's both administrators and users, so you must be careful assigning this to a employee.
		
[usuarios-.zip](https://github.com/Ignacio-Pavone/Naiglu-Sales-System/files/8845561/usuarios-.zip)

NON-ADMIN USER

![image](https://user-images.githubusercontent.com/84025828/172193300-611d5e90-7526-4172-9e15-df91d929bd18.png)

ADMIN USER

![image](https://user-images.githubusercontent.com/84025828/172193391-872d10cd-426e-4c36-8b69-bce59470a619.png)


Where to start?

It is recommended to begin at "Business" tab, where you can set up the name of the company and fill in other details.

![image](https://user-images.githubusercontent.com/84025828/172194246-5b30dafd-557e-4ede-801b-31fe34563a92.png)

Then, you must add suppliers.

![image](https://user-images.githubusercontent.com/84025828/172193661-b5dd1668-5d85-48b4-a608-4b06a8964766.png)

After filling all the fields, click on add supplier.

Now you can add products.

![image](https://user-images.githubusercontent.com/84025828/172193972-394fc0ff-dc0a-4689-9702-c4dd98b4bec5.png)

You can select any supplier from the drop-down menu. Keep in mind that the "Price" will be deducted from the business's balance, since it is essentially a purchase.

![image](https://user-images.githubusercontent.com/84025828/172195371-c2e2cb8b-fab6-4875-9b69-2c8d6a7991ad.png)

Before we can sell any products, we need to add customers.

![image](https://user-images.githubusercontent.com/84025828/172194364-c3eb090a-3e45-4075-9d02-fd6ff30f34fa.png)

Quite similar to the "Add suppliers" tab, you must fill in the customer's data and then you're done.


Moving on to selling items, after loading a bunch of customers, suppliers and items, you will find this in the product list tab.

![image](https://user-images.githubusercontent.com/84025828/172194633-4772bf28-0604-46a8-9c8c-b7214775b66b.png)

Select a row and add the amount of items needed of each product. Those will be added to a cart.

![image](https://user-images.githubusercontent.com/84025828/172194967-c41ebd94-68f2-480c-91a0-6b6da567975e.png)

If you want to confirm the purchase, first select the right customer, and then press confirm. This will make the purchase go to the Sales tab.

![image](https://user-images.githubusercontent.com/84025828/172195182-5c9c9f90-a6ed-430d-af4e-7b0af8aad04e.png)

On the sales tab, you can generate invoices and see previous invoices for your sales. 

![image](https://user-images.githubusercontent.com/84025828/172195460-ee984046-4bce-4d56-bacb-42fee71863b9.png)

After you're done with the day, you can close the desk and see the statistics on its tab. You can clear those too if you wish.

![image](https://user-images.githubusercontent.com/84025828/172195590-c630e177-8e45-45b0-8f3f-417f131c2906.png)

As you can see, the purchase was reflected on the account's balance.

![image](https://user-images.githubusercontent.com/84025828/172195701-ddc0ad79-af81-4bcf-8c27-b0229d19a468.png)

























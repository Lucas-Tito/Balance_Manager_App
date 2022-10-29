# Balance Manager App 📱


<p align="center">
  <img src="https://user-images.githubusercontent.com/61806906/198152657-cfb8c953-4f5a-4af8-86ce-58cae022fe9c.png" alt="screenshot 1" width="20%" height="20%"/>
  <img src="https://user-images.githubusercontent.com/61806906/198153631-c2633cd7-17f5-4f11-b734-d9e3defb0339.png" alt="screenshot 1" width="20%" height="20%"/>
</p>

<h4 align="center">
  The development of this app is on going and it still in early stages.
</h4>

<h6 align="center"> 
  <a href= "https://docs.google.com/document/d/1uwf9_W-jRhgGxdPYPdML0cEH8OpjavBO2DPHnatFzCg/edit"> Specification Doc </a>
</h6>
  
  
<br><br><br><br><br><br>

<h2 align="center">
  Boring Stuff 😴
</h2>

### ToDo list (Tito)

+ recycleView need to implement a list view in recycleViewItem (for only one date by day is showed).
+ janky abstract class (Transactions).
+ Edit Expense/Income aren't using editExpense/Income methods from their respectives DAOs.


#### Minor Issues
+ use  myAdapter.notifyDataSetChanged() when refreshing recycle view.
+ need to create a attribute on main acitivty to hold total amount (income-expense) to other activities only need to access this attribute to protrai it.
+ (in Edit_Transaction_Activity) make fragToStart a global variable, since it's used everywhere in this activity.

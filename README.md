- Purpose: Show the user’s contact list, sorted by a Rank value, in decreasing order
- In an activity, show a list view (can be any view group of your choice, which shows a vertical scrolling list), which loads the contact list from your phone.

- The list view should load 20 contacts at a time.

- When you scroll down, it should load 20 more contacts. The contents of the list view items can be Name, Phone number

- If I tap on a contact, you should implement a RANK UP on the contact. Every contact you load will by default have a rank = 0. On every interaction on a contact, the rank gets incremented. This value should be persisted permanently, until the app is uninstalled (Your can implement your own persistent storage for this).

- On every Rank up, the position of the item in the list view should change

- If I rotate the phone, the state of the Activity should be preserved


Demo: https://drive.google.com/open?id=1cHvVCXZSLyY_xyqvv1vFm3LxA4rYuTCr

# Purple Blog

Purple Blog is a tech-based blogging app that allows its users to create their own blogs for other users to read. 
Authors, or people who have created blogs, can also assign users to be editors of their blogs, or revoke those permissions when necessary. 

Login Activity
  -Login/SRegister Activities
    Users can login/register, which will query information from the project's Firestore.
  -Main Activity
        When signed in, users will be presented a RecyclerView of views that, when pressed, will take the user to said views' 
        respective blog. These views may include information such as the title of the blog, the username, 
        blog topic, time stamp, blog post etc.
        
        An option to create an article will be available in the options menu. If the user presses this, a blank 
        blog template is shown.
  -Article Activity
    *NewPostActivity
      Allows the user to create a new blog post to add to their list of blogs already in the activity
    *ViewBlog
      When a user navigates to some article, they are presented with most of the information associated 
      with the article, such as the title, author, content, date of creation, etc.

      The author of an article (or an editor designated by the author) will have an option in the options menu to edit the article


Contributors
Shannen Bravo-Brown 
Sean Ebanks 
Bria Bostick 

** Austin had nothing to do with this project. he wrote no code, did not help with the essay nor did he help with the powerpoint **

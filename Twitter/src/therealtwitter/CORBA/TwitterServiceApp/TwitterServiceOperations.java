package therealtwitter.CORBA.TwitterServiceApp;


/**
* therealtwitter/CORBA/TwitterServiceApp/TwitterServiceApp/TwitterServiceOperations.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from TwitterService.idl
* mercredi 20 juin 2018 00 h 12 CEST
*/

public interface TwitterServiceOperations 
{
  String ping ();
  String getMyInfo ();
  String getUserInfo (String username);
  String postTweet (String tweetText);
  String getFeed ();
  String connect (String password, String username);
  void shutdown ();
} // interface TwitterServiceOperations

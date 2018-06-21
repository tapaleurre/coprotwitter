package therealtwitter.CORBA.TwitterServiceApp;


/**
* therealtwitter/CORBA/TwitterServiceApp/_TwitterServiceStub.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from TwitterService.idl
* jeudi 21 juin 2018 22 h 39 CEST
*/

public class _TwitterServiceStub extends org.omg.CORBA.portable.ObjectImpl implements therealtwitter.CORBA.TwitterServiceApp.TwitterService
{

  public String ping ()
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("ping", true);
                $in = _invoke ($out);
                String $result = $in.read_string ();
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return ping (        );
            } finally {
                _releaseReply ($in);
            }
  } // ping

  public String getUserInfo (String username)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("getUserInfo", true);
                therealtwitter.CORBA.TwitterServiceApp.UsernameHelper.write ($out, username);
                $in = _invoke ($out);
                String $result = $in.read_string ();
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return getUserInfo (username        );
            } finally {
                _releaseReply ($in);
            }
  } // getUserInfo

  public String postTweet (String tweetText, String username, double privateKey)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("postTweet", true);
                therealtwitter.CORBA.TwitterServiceApp.TweetTextHelper.write ($out, tweetText);
                therealtwitter.CORBA.TwitterServiceApp.UsernameHelper.write ($out, username);
                therealtwitter.CORBA.TwitterServiceApp.PrivateKeyHelper.write ($out, privateKey);
                $in = _invoke ($out);
                String $result = $in.read_string ();
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return postTweet (tweetText, username, privateKey        );
            } finally {
                _releaseReply ($in);
            }
  } // postTweet

  public String getFeed (String username)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("getFeed", true);
                therealtwitter.CORBA.TwitterServiceApp.UsernameHelper.write ($out, username);
                $in = _invoke ($out);
                String $result = $in.read_string ();
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return getFeed (username        );
            } finally {
                _releaseReply ($in);
            }
  } // getFeed

  public double connect (String password, String username)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("connect", true);
                therealtwitter.CORBA.TwitterServiceApp.PasswordHelper.write ($out, password);
                therealtwitter.CORBA.TwitterServiceApp.UsernameHelper.write ($out, username);
                $in = _invoke ($out);
                double $result = $in.read_double ();
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return connect (password, username        );
            } finally {
                _releaseReply ($in);
            }
  } // connect

  public String follow (String username, double privateKey, String secondUser)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("follow", true);
                therealtwitter.CORBA.TwitterServiceApp.UsernameHelper.write ($out, username);
                therealtwitter.CORBA.TwitterServiceApp.PrivateKeyHelper.write ($out, privateKey);
                therealtwitter.CORBA.TwitterServiceApp.SecondUserHelper.write ($out, secondUser);
                $in = _invoke ($out);
                String $result = $in.read_string ();
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return follow (username, privateKey, secondUser        );
            } finally {
                _releaseReply ($in);
            }
  } // follow

  public void shutdown ()
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("shutdown", false);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                shutdown (        );
            } finally {
                _releaseReply ($in);
            }
  } // shutdown

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:TwitterServiceApp/TwitterService:1.0"};

  public String[] _ids ()
  {
    return (String[])__ids.clone ();
  }

  private void readObject (java.io.ObjectInputStream s) throws java.io.IOException
  {
     String str = s.readUTF ();
     String[] args = null;
     java.util.Properties props = null;
     org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init (args, props);
   try {
     org.omg.CORBA.Object obj = orb.string_to_object (str);
     org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl) obj)._get_delegate ();
     _set_delegate (delegate);
   } finally {
     orb.destroy() ;
   }
  }

  private void writeObject (java.io.ObjectOutputStream s) throws java.io.IOException
  {
     String[] args = null;
     java.util.Properties props = null;
     org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init (args, props);
   try {
     String str = orb.object_to_string (this);
     s.writeUTF (str);
   } finally {
     orb.destroy() ;
   }
  }
} // class _TwitterServiceStub

# JSON API Examples for Liferay 6.1

This little project demonstrates how the documentation and available info on the web says the JSON Web Service APIs created by Liferay Service Builder should work.

WARNING: This is how it is *supposed* to work, as best I can tell.  As you will see, it doesn't really work.

My hope is that this will inspire knowledgeable people (Listening Liferay?) to fork this repository and turn it into an actual working example.  I imagine some of this will be fixing my misperceptions of how it is supposed to be, and some will be fixing documentation, and maybe even fixing Service Builder.

The use-cases I have here now are just the ones I'm looking to use.  There are others, and the documentation in the [Liferay Developer Guide, Chapter 8](https://github.com/liferay/liferay-docs/blob/master/devGuide/en/chapters/08-apis.markdown) is pretty good -- except where it isn't.

I'll happily incorporate any pull request that corrects something or adds a functioning example or use-case. (as long as it has to do with Service Builder generated Web Services.)

The project is a standard Liferay SDK project generated by Liferay Developer Studio.  My environment is Liferay 6.1 EE (6110), but it should (er) work on 6.1 CE too.  I'm running Liferay in Tomcat 7.

The basic steps are:

1. Create a service builder entity with remote service attribute set to true and generate the service.
2. Configure your portlet with a servlet descriptor that wires in your api.  This occurs in web.xml.
3. Make sure the service builder javascript is included in your portlet, this occurs in the javascript elements of the portlet element of your liferay-portlet.xml file.
4. Build and deploy the portlet
5. Place the portlet on a page

The repo _should_ cover the first 3 steps unless there's a mistake somewhere.

Some people seem to think you need to unpack your war file, extract the service jar and put it in Tomcat's globally privileged lib/ext.  I havne't been able to confirm this, and I don't see any difference in the behavior when I try.  So this topic is another one where I'd love to have an explanation.

Now given the portlet in this project, theoretically you should be able to do all of the following things.

## Make ajax calls directly to the service from inside your portlet.

Like this,

    Liferay.Service.jsonsw_example.Foo.print(
        data = {msg:'Hey'},
        successCallback = function(m) {
          result2.text('Success: '+ m);
        },
        exceptionCallback = function(m) {
          result2.text('Exception: '+ m);
        }
    );

or this,

    Liferay.Service(
        service = 'jsonsw-example-portlet/Foo/print',
        data = {msg:'Foo'},
        successCallback = function(m) {
          result1.text('Success: '+ m);
        },
        exceptionCallback = function(m) {
          result1.text('Exception: '+ m);
        }
    );


You can't, though, but if I understand what I've found out there on the web, you are supposed to be able to do this.  I'd love to know what I'm missing here.  The first example results in an Array Out of Bounds exception, and the second one results in a URL not being found.

NOTE -- I believe the javascript api is simply generating the wrong URL

    /jsonsw-example-portlet/api/json
    
which I believe should be

    /jsonsw-example-portlet/api/jsonws
    
## Make authenticated calls from elsewhere.

Like this,

    curl http://localhost:8080/jsonsw-example-portlet/api/secure/jsonws/foo/print \
          -utest@liferay.com:test \
          -d msg='I want my Foo!'

    # reply
    # "{\"Message\":\"I want my Foo!\"}"%

    
which seems to work.  Unauthenticated calls should not work

    curl http://localhost:8080/jsonsw-example-portlet/api/jsonws/foo/print \
          -utest@liferay.com:test \
          -d msg='I want my Foo!'

    # reply
    # "{\"Message\":\"I want my Foo!\"}"%


or
  
    curl http://localhost:8080/jsonsw-example-portlet/api/jsonws/foo/print \
          -d msg='I want my Foo!'

    # reply
    # "{\"Message\":\"I want my Foo!\"}"%

but they do.


## Visit your portal's API page

    http://localhost:8080/jsonsw-example-portlet/api/jsonws
    
or

    http://localhost:8080/jsonsw-example-portlet/api/secure/jsonws
    
On my system the second one just redirects to the first.

Running the 'print' api call should give you some results.  But it doesn't.

Invoking the method with a string in the input box results in an exception as a reply, and an Array Out of Bounds Exception in Catalina.out.


So it looks like the URL is backwards again when that page tries to execute the form.   It produces

    http://localhost:8080/api/jsonws/jsonsw-example-portlet/foo/print;<querystring>
    
when it should be producing

    http://localhost:8080/jsonsw-example-portlet/api/jsonws/foo/print;<querystring>



Invoking the example curl command doesn't work either, you need to use the URL I gave above instead of the one here.  If you try the example curl on the command line, it looks like this:

    curl http://localhost:8080/api/secure/jsonws/jsonsw-example-portlet/foo/print \
      -u test@liferay.com:test \
      -d formDate=1336338170038 \
      -d msg='hey there'

    # Reply
    # {"exception":"1"}
    
    
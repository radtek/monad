function analytics_onready(){
  Module.SetApiUrl("http://localhost:9081/api");
}
importScripts("async.min.js")
importScripts('../build-em/em/monad_analytics.js');
importScripts('analytics.js');
importScripts('ops.js');

Analytics.config.fail = function(fail_message){
  postMessage({op:OP_FAIL,message:fail_message})
}
Analytics.config.progress= function(progress_message){
  postMessage({op:OP_PROGRESS,message:progress_message})
}
var method_mapper={}
method_mapper[OP_QUERY]= Analytics.query

onmessage=function(event){
  //console.log("op:"+event.data.op+" index:"+event.data.i+" q:"+event.data.q)
  var op = event.data.op;
  switch(op){
    case OP_TOP:
      Analytics.top(function(objects){postMessage({op:op,result:objects})},event.data.parameters)
      break;
    default:
      var condition = Analytics.createCondition();
      var parameters = event.data.parameters;
      condition.query_objects = parameters.queries;
      condition.freq = parameters.freq;
      condition.op = op

      condition.execute(function(coll){
        postMessage({op:op,result:coll});
      })
  }


/*
      Analytics.config.progress("creating wrapper ...")
      var wrapper_object = Analytics.createBitSetWrapper();
      var bit_set_wrapper = wrapper_object.wrapper;
      bit_set_wrapper.NewSeg(1,100000000)
      for(i=0;i<10000000;i++ ){
        bit_set_wrapper.FastSet(i*3);
      }
      bit_set_wrapper.Commit();

      Analytics.config.progress("wrapper created");
      //assert.ok(bit_set_wrapper.FastGet(100))

      Analytics.inPlaceAnd([
        wrapper_object.key,
        wrapper_object.key,
        wrapper_object.key,
        ],function(result){
            Analytics.config.progress("finish inPlaceAnd ,count:"+result.count)
      });
      Analytics.config.progress("end inPlace And");
      */

}
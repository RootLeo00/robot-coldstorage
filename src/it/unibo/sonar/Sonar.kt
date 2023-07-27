/* Generated by AN DISI Unibo */ 
package it.unibo.sonar

import it.unibo.kactor.*
import alice.tuprolog.*
import unibo.basicomm23.*
import unibo.basicomm23.interfaces.*
import unibo.basicomm23.utils.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Sonar ( name: String, scope: CoroutineScope  ) : ActorBasicFsm( name, scope ){

	override fun getInitialState() : String{
		return "s0"
	}
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		val interruptedStateTransitions = mutableListOf<Transition>()
		 val DLIMIT = 70 ; 
			   var stopped=false;
			   
			   	var startInstant:Long = 0;
				val MINT: Long=4000;
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						CommUtils.outblack("sonar | start")
						 subscribeToLocalActor("distancefilter").subscribeToLocalActor("datacleaner").subscribeToLocalActor("sonarfisico")  
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="work", cond=doswitch() )
				}	 
				state("work") { //this:State
					action { //it:State
						updateResourceRep( "sonar waiting ..."  
						)
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition(edgeName="t024",targetState="handlesonardata",cond=whenEvent("sonardata"))
					transition(edgeName="t025",targetState="handleobstacle",cond=whenEvent("obstacle"))
				}	 
				state("handlesonardata") { //this:State
					action { //it:State
						CommUtils.outcyan("$name in ${currentState.stateName} | $currentMsg | ${Thread.currentThread().getName()} n=${Thread.activeCount()}")
						 	   
						updateResourceRep( "sonar handles $currentMsg"  
						)
						if( checkMsgContent( Term.createTerm("distance(D)"), Term.createTerm("distance(D)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								 var D = payloadArg(0).toInt()  
								if(  D >= DLIMIT && stopped == true  
								 ){CommUtils.outred("$name | resume transport trolley")
								forward("endalarm", "endalarm" ,"transporttrolley" ) 
								 stopped = false;
													startInstant=System.currentTimeMillis();
								}
						}
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="work", cond=doswitch() )
				}	 
				state("handleobstacle") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("obstacle(D)"), Term.createTerm("obstacle(D)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								 var endInstant=  System.currentTimeMillis();   
									 	 	   var deltatime= (endInstant - startInstant); //tempo trascorso dall'ultima resume
								CommUtils.outred("$name | end-start= $deltatime >/< MINT=$MINT")
								if(  startInstant==0L || deltatime >= MINT 
								 ){CommUtils.outred("$name handleobstacle ALARM ${payloadArg(0)}")
								forward("stopobstacle", "stopobstacle" ,"transporttrolley" ) 
								 stopped = true;  
								}
								else
								 {CommUtils.outmagenta("$name alarm IGNORED")
								 }
						}
						//genTimer( actor, state )
					}
					//After Lenzi Aug2002
					sysaction { //it:State
					}	 	 
					 transition( edgeName="goto",targetState="work", cond=doswitch() )
				}	 
			}
		}
}

/**
 * 	Generated by mxmlc 2.0
 *
 *	Package:	Components
 *	Class: 		AmbiguityPanel_inlineComponent1
 *	Source: 	AmbiguityPanel_inlineComponent1
 *	Template: 	flex2/compiler/mxml/gen/ClassDef.vm
 *	Time: 		2007.09.21 11:53:38 GMT+05:30
 */

package Components
{

import Components.AmbiguityPanel;
import flash.accessibility.*;
import flash.debugger.*;
import flash.display.*;
import flash.errors.*;
import flash.events.*;
import flash.events.MouseEvent;
import flash.external.*;
import flash.filters.*;
import flash.geom.*;
import flash.media.*;
import flash.net.*;
import flash.printing.*;
import flash.profiler.*;
import flash.system.*;
import flash.text.*;
import flash.ui.*;
import flash.utils.*;
import flash.xml.*;
import mx.binding.*;
import mx.controls.CheckBox;
import mx.core.ClassFactory;
import mx.core.DeferredInstanceFromClass;
import mx.core.DeferredInstanceFromFunction;
import mx.core.IDeferredInstance;
import mx.core.IFactory;
import mx.core.IPropertyChangeNotifier;
import mx.core.mx_internal;
import mx.styles.*;



public class AmbiguityPanel_inlineComponent1
	extends mx.controls.CheckBox
{

	[Bindable]
/**
 * @private
 **/
	public var outerDocument : Components.AmbiguityPanel;





    /**
     * @private
     **/
	public function AmbiguityPanel_inlineComponent1()
	{
		super();


		//	our style settings



		//	properties
		this.name = "checkBox";

		//	events
		this.addEventListener("click", ___CheckBox1_click);

	}

    /**
     * @private
     **/
	override public function initialize():void
	{

		//	binding mgmt
		_AmbiguityPanel_inlineComponent1_bindingsSetup();

		var target:AmbiguityPanel_inlineComponent1 = this;

		if (_watcherSetupUtil == null)
		{
			var watcherSetupUtilClass:Object = getDefinitionByName("_Components_AmbiguityPanel_inlineComponent1WatcherSetupUtil");
			watcherSetupUtilClass["init"](null);
		}

		_watcherSetupUtil.setup(this,
					function(propertyName:String):* { return target[propertyName]; },
					_bindings,
					_watchers);


		super.initialize();
	}



    //	supporting function definitions for properties, events, styles, effects
/**
 * @private
 **/
public function ___CheckBox1_click(event:flash.events.MouseEvent):void
{
	data.isSelected=!data.isSelected
}


	//	binding mgmt
    private var _bindings:Array;
    private var _watchers:Array;
    private function _AmbiguityPanel_inlineComponent1_bindingsSetup():void
    {
        if (!_bindings)
        {
            _bindings = [];
        }

        if (!_watchers)
        {
            _watchers = [];
        }

        var binding:Binding;

        binding = new mx.binding.Binding(this,
            function():Boolean
            {
                return (data.isSelected);
            },
            function(_sourceFunctionReturnValue:Boolean):void
            {
				
                this.selected = _sourceFunctionReturnValue;
            },
            "this.selected");
        _bindings[0] = binding;
    }

    private function _AmbiguityPanel_inlineComponent1_bindingExprs():void
    {
        var destination:*;
		[Binding(id='0')]
		destination = (data.isSelected);
    }

    /**
     * @private
     **/
    public static function set watcherSetupUtil(watcherSetupUtil:IWatcherSetupUtil):void
    {
        (AmbiguityPanel_inlineComponent1)._watcherSetupUtil = watcherSetupUtil;
    }

    private static var _watcherSetupUtil:IWatcherSetupUtil;





    /**
     * @private
     **/
    public var _bindingsByDestination : Object;
    /**
     * @private
     **/
    public var _bindingsBeginWithWord : Object;

}

}

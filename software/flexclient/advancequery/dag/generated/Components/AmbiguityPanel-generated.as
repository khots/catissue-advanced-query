/**
 * 	Generated by mxmlc 2.0
 *
 *	Package:	Components
 *	Class: 		AmbiguityPanel
 *	Source: 	F:\workspace\AdvancedQuery\flexclient\dag\Components\AmbiguityPanel.mxml
 *	Template: 	flex2/compiler/mxml/gen/ClassDef.vm
 *	Time: 		2008.09.26 14:24:16 GMT+05:30
 */

package Components
{

import Components.CheckBoxItemRenderer;
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
import mx.containers.ControlBar;
import mx.containers.Panel;
import mx.controls.Button;
import mx.controls.DataGrid;
import mx.controls.Spacer;
import mx.controls.dataGridClasses.DataGridColumn;
import mx.core.ClassFactory;
import mx.core.DeferredInstanceFromClass;
import mx.core.DeferredInstanceFromFunction;
import mx.core.IDeferredInstance;
import mx.core.IFactory;
import mx.core.IPropertyChangeNotifier;
import mx.core.UIComponentDescriptor;
import mx.core.mx_internal;
import mx.events.FlexEvent;
import mx.styles.*;



public class AmbiguityPanel
	extends mx.containers.Panel
{

	[Bindable]
	public var _DataGrid1 : mx.controls.DataGrid;




private var _documentDescriptor_ : mx.core.UIComponentDescriptor = 
new mx.core.UIComponentDescriptor({
  type: mx.containers.Panel
  ,
  propertiesFactory: function():Object { return {
    width: 520,
    height: 390,
    childDescriptors: [
      new mx.core.UIComponentDescriptor({
        type: mx.controls.DataGrid
        ,
        id: "_DataGrid1"
        ,
        propertiesFactory: function():Object { return {
          name: "pathGrid",
          width: 500,
          x: 0,
          height: 315,
          y: 0,
          verticalScrollPolicy: "on",
          horizontalScrollPolicy: "off",
          columns: [_DataGridColumn1_c(), _DataGridColumn2_c()]
        }}
      })
    ,
      new mx.core.UIComponentDescriptor({
        type: mx.containers.ControlBar
        ,
        propertiesFactory: function():Object { return {
          name: "controlBar",
          childDescriptors: [
            new mx.core.UIComponentDescriptor({
              type: mx.controls.Spacer
              ,
              propertiesFactory: function():Object { return {
                percentWidth: 100.0
              }}
            })
          ,
            new mx.core.UIComponentDescriptor({
              type: mx.controls.Button
              ,
              events: {
                click: "___Button1_click"
              }
              ,
              propertiesFactory: function():Object { return {
                name: "OK",
                label: "OK"
              }}
            })
          ,
            new mx.core.UIComponentDescriptor({
              type: mx.controls.Button
              ,
              events: {
                click: "___Button2_click"
              }
              ,
              propertiesFactory: function():Object { return {
                name: "Cancel",
                label: "Cancel"
              }}
            })
          ]
        }}
      })
    ]
  }}
})

	public function AmbiguityPanel()
	{
		super();

		mx_internal::_document = this;

		//	our style settings



		//	properties
		this.layout = "absolute";
		this.width = 520;
		this.height = 390;
		this.verticalScrollPolicy = "off";
		this.horizontalScrollPolicy = "off";

		//	events
		this.addEventListener("creationComplete", ___Panel1_creationComplete);

	}

	override public function initialize():void
	{
 		mx_internal::setDocumentDescriptor(_documentDescriptor_);

		//	binding mgmt
		_AmbiguityPanel_bindingsSetup();

		var target:AmbiguityPanel = this;

		_watcherSetupUtil.setup(this,
					function(propertyName:String):* { return target[propertyName]; },
					_bindings,
					_watchers);


		super.initialize();
	}


		import mx.controls.DataGrid;
		import mx.controls.CheckBox;
		import mx.binding.utils.BindingUtils;
		import mx.collections.ArrayCollection;
		import mx.managers.PopUpManager;
	    import mx.events.CollectionEvent;
	    import Components.DAGPath;
	    import mx.controls.Alert;
		import mx.managers.PopUpManager;
		import mx.rpc.events.ResultEvent;


	    [Bindable]
		public var pathList:ArrayCollection=new ArrayCollection();;
	    [Bindable]
		public var selectedList:ArrayCollection= null;
		public var nodeList:ArrayCollection =null;						
		public var dagPath:DAGPath = null;
		
		/**
		* Initialization ambiguity panel.
		*/
		private function init():void
        {
        	selectedList = new ArrayCollection()
        	var dg :DataGrid = this.getChildByName("pathGrid") as DataGrid;
        	dg.dataProvider=pathList;
			addEventListener(ClickEvent.CLICK, onSimpleClickEvent);
        }
       /**
       * OK & Cancel event Handler
       */
		public  function closePopUp(event:MouseEvent):void {
				var buttonStr:Array=event.currentTarget.toString().split(".");
				var index:int=(buttonStr.length-1)
	   	 	 	PopUpManager.removePopUp(this);
				if(buttonStr[index]=="OK")
				{
					var len:int = pathList.length;
					for (var i:int=0;i<len;i++)
					{
						dagPath = pathList.getItemAt(i) as DAGPath;
						if (dagPath.isSelected)
						{
							selectedList.addItem(dagPath);
						}
					}
					
					this.parentApplication.rpcService.removeEventListener(ResultEvent.RESULT,this.parentApplication.getPathHandler,false);
					this.parentApplication.rpcService.addEventListener("result",this.parentApplication.linkNodesHandler);
					if(nodeList!=null&&selectedList!=null)
					{
						if(selectedList.length > 1)
						{
							Alert.show("Multiple associations not yet supoorted.");		
							this.parentApplication.rpcService.removeEventListener(ResultEvent.RESULT,this.parentApplication.linkNodesHandler,false);
						}
						else
						{
							this.parentApplication.rpcService.linkNodes(nodeList,selectedList); 
							selectedList.removeAll();
						}
					}
				}
				else
				{
					selectedList=null;
					pathList=null;
					this.parentApplication.cancelHandler(nodeList);
				}
		}

		/**
		* This is the function that will catch the click event
		* that will bubble up from the Checkbox in our DataGrid's itemRenderer
		*/
		private function onSimpleClickEvent(e:ClickEvent):void
		{
			// create refs to the items we care about
			var ir:CheckBoxItemRenderer = e.target as CheckBoxItemRenderer;
			var cb:CheckBox = ir.cb as CheckBox;
			var dagPathVo:DAGPath = e.vo as DAGPath;
		
			// update the appropriate data property based on the CheckBox's selected state
			if(cb.selected)
			{
				dagPathVo.isSelected = true;
			}
			else
			{
				dagPathVo.isSelected = false;
			}
		}
		
	



    //	supporting function definitions for properties, events, styles, effects
public function ___Panel1_creationComplete(event:mx.events.FlexEvent):void
{
	init()
}

private function _DataGridColumn1_c() : mx.controls.dataGridClasses.DataGridColumn
{
	var temp : mx.controls.dataGridClasses.DataGridColumn = new mx.controls.dataGridClasses.DataGridColumn();
	temp.headerText = "Paths";
	temp.dataField = "toolTip";
	temp.dataTipField = "toolTip";
	temp.showDataTips = true;
	temp.width = 440;
	temp.wordWrap = true;
	return temp;
}

private function _DataGridColumn2_c() : mx.controls.dataGridClasses.DataGridColumn
{
	var temp : mx.controls.dataGridClasses.DataGridColumn = new mx.controls.dataGridClasses.DataGridColumn();
	temp.headerText = "Select";
	temp.dataField = "isSelected";
	temp.width = 60;
	temp.itemRenderer = _ClassFactory1_c();
	temp.rendererIsEditor = true;
	temp.editorDataField = "selected";
	return temp;
}

private function _ClassFactory1_c() : mx.core.ClassFactory
{
	var temp : mx.core.ClassFactory = new mx.core.ClassFactory();
	temp.generator = Components.CheckBoxItemRenderer;
	return temp;
}

public function ___Button1_click(event:flash.events.MouseEvent):void
{
	closePopUp(event)
}

public function ___Button2_click(event:flash.events.MouseEvent):void
{
	closePopUp(event)
}


	//	binding mgmt
    private var _bindings:Array;
    private var _watchers:Array;
    private function _AmbiguityPanel_bindingsSetup():void
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
            function():Object
            {
                return (pathList);
            },
            function(_sourceFunctionReturnValue:Object):void
            {
				
                _DataGrid1.dataProvider = _sourceFunctionReturnValue;
            },
            "_DataGrid1.dataProvider");
        _bindings[0] = binding;
    }

    private function _AmbiguityPanel_bindingExprs():void
    {
        var destination:*;
		[Binding(id='0')]
		destination = (pathList);
    }

    public static function set watcherSetupUtil(watcherSetupUtil:IWatcherSetupUtil):void
    {
        (AmbiguityPanel)._watcherSetupUtil = watcherSetupUtil;
    }

    private static var _watcherSetupUtil:IWatcherSetupUtil;





    public var _bindingsByDestination : Object;
    public var _bindingsBeginWithWord : Object;

}

}

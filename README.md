# RecyclerViewDatabindingAdapter

### 接入
> Step 1.将JitPack库添加到project gradle 的依赖当中

  <pre>allprojects {
		repositories {
			maven { url 'https://jitpack.io' }
		}
	}  </pre>

  
> Step 2. app gradle 添加依赖

  <pre>
  dependencies {
	        compile 'com.github.zhangshuqi:RecyclerViewDatabindingAdapter:v1.0.2'
          }
     </pre>


### 使用:
> 单个itemViewType  单个foot | head使用  SingleTypeBindingAdapter 

&ensp;&ensp;&ensp;
```
		SingleTypeBindingAdapter adapter = new SingleTypeBindingAdapter(this, data, R.layout.item_list);
```


> 多个itemViewType  多个foot | head使用  MultiTypeBindingAdapter 

&ensp;&ensp;&ensp;
```
		MultiTypeBindingAdapter adapter = new MultiTypeBindingAdapter(this, data, R.layout.item_list);
```



#怎么使用看代码吧 markdown 真鸡儿难用
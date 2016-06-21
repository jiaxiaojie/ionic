<?php

 /**
  * @Title 易宝支付分账范例
  * @Description Map类
  * @Copyriht (c) 北京通融通信息有限公司（易宝支付）
  * @Author    wenhua.cheng
  * @Version   V2.0  
  */
  
class Map implements IteratorAggregate,ArrayAccess,Countable   
{   
    private $data = array();   
       
    public function put($key,$value)   
    {   
        $this->data[$key] = $value;   
    }   
       
    public function remove($key)   
    {   
        if(isset($this->data[$key]))   
        {   
            $value = $this->data[$key];   
            unset($this->data[$key]);   
            return $value;   
        }   
        return null;   
    }   
       
    public function clean()   
    {   
        foreach($this->getKeys() as $key)   
        {   
            $this->remove($key);   
        }   
    }   
       
    public function getKeys()   
    {   
        return array_keys($this->data);   
    }   
     public function getKeysValues()   
    {   
        return array_keys($this->data);   
    }   
        
    public function getValueAt($key)   
    {   
        if(isset($this->data[$key]))   
            return $this->data[$key];   
        else  
            return null;   
    }   
    public function copyData($data)   
    {   
        if(is_array($data))   
        {   
            foreach($data as $key=>$value)   
            {   
                $this->put($key,$value);   
            }   
        }   
    }   

    public static function dataMerge($dataOne,$dataTwo)   
    {   
        foreach($dataTwo as $key=>$value)   
        {   
            if(is_integer($key))   
                $dataOne[] = $value;   
            elseif(isset($dataOne[$key]) && is_array($dataOne[$key]) && is_array($value))   
                $dataOne[$key] = self::dataMerge($dataOne[$key],$value);   
            else  
                $dataOne[$key] = $value;   
        }   
    }   
    public function getCount()   
    {   
        return count($this->data);   
    }   
    public function toArray()   
    {   
        return $this->data;   
    }   
	
    /* 实现spl接口 */  
    public function getIterator()   
    {   
        return new MapobIterator($this->data);    
    }   
  
    public function count()   
    {   
        return $this->getCount();   
    }   
           
    public function offsetExists($offset)   
    {   
        return $this->data[$offset]!==null;   
    }   
  
    public function offsetGet($offset)   
    {   
        return $this->itemAt($offset);   
    }   
  
    public function offsetSet($offset,$item)   
    {   
        $this->put($offset,$item);   
    }   
  
    public function offsetUnset($offset)   
    {   
        $this->remove($offset);   
    }   
}   
  
class MapobIterator implements Iterator   
{   
    private $data = array();   
    private $keys = array();   
    public function __construct(&$data)   
    {   
        $this->data = &$data;   
        $this->keys = array_keys($data);   
    }   
  
    public function rewind()   
    {   
        $this->key = reset($this->keys);   
    }   
  
    public function key()   
    {   
        return $this->key;   
    }   
  
    public function current()   
    {   
        return $this->data[$this->key];   
    }   
  
    public function next()   
    {   
        $this->key = next($this->keys);   
    }   
  
    public function valid()   
    {   
        return $this->key!==false;   
    }   
}   

?>
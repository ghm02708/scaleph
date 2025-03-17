import React, {useEffect} from 'react';
import {Form} from 'antd';
import {InfoCircleOutlined} from '@ant-design/icons';
import {
  DrawerForm,
  ProFormDigit,
  ProFormGroup,
  ProFormList,
  ProFormSelect,
  ProFormText,
  ProFormTextArea,
} from '@ant-design/pro-components';
import {getIntl, getLocale} from "@umijs/max";
import {Node, XFlow} from '@antv/xflow';
import {ModalFormProps} from '@/typings';
import {FakeParams, SchemaParams, STEP_ATTR_TYPE} from '../constant';
import {StepSchemaService} from '../helper';
import {DictDataService} from "@/services/admin/dictData.service";
import {DICT_TYPE} from "@/constants/dictType";

const SourceFakeStepForm: React.FC<ModalFormProps<Node>> = ({data, visible, onVisibleChange, onOK}) => {
  const intl = getIntl(getLocale());
  const [form] = Form.useForm();

  useEffect(() => {
    form.setFieldValue(STEP_ATTR_TYPE.stepTitle, data.data.label);
  }, []);

  return (
    <XFlow>
      <DrawerForm
        title={data.data.label}
        form={form}
        initialValues={data.data.attrs}
        open={visible}
        onOpenChange={onVisibleChange}
        grid={true}
        width={780}
        drawerProps={{
          styles: {body: {overflowY: 'scroll'}},
          closeIcon: null,
          destroyOnClose: true
        }}
        onFinish={(values) => {
          if (onOK) {
            StepSchemaService.formatSchema(values);
            onOK(values)
            return Promise.resolve(true)
          }
          return Promise.resolve(false)
        }}
      >
        <ProFormText
          name={STEP_ATTR_TYPE.stepTitle}
          label={intl.formatMessage({id: 'pages.project.di.step.stepTitle'})}
          rules={[{required: true}, {max: 120}]}
        />
        <ProFormTextArea
          name={FakeParams.tablesConfigs}
          label={intl.formatMessage({id: 'pages.project.di.step.fake.tablesConfigs'})}
          tooltip={{
            title: intl.formatMessage({id: 'pages.project.di.step.fake.tablesConfigs.tooltip'}),
            icon: <InfoCircleOutlined/>,
          }}
        />
        <ProFormTextArea
          name={FakeParams.rows}
          label={intl.formatMessage({id: 'pages.project.di.step.fake.rows'})}
        />
        <ProFormDigit
          name={FakeParams.rowNum}
          label={intl.formatMessage({id: 'pages.project.di.step.fake.rowNum'})}
          colProps={{span: 8}}
          initialValue={10}
          fieldProps={{
            step: 100,
            min: 1,
          }}
        />
        <ProFormDigit
          name={FakeParams.splitNum}
          label={intl.formatMessage({id: 'pages.project.di.step.fake.splitNum'})}
          tooltip={{
            title: intl.formatMessage({id: 'pages.project.di.step.fake.splitNum.tooltip'}),
            icon: <InfoCircleOutlined/>,
          }}
          colProps={{span: 8}}
          initialValue={1}
          fieldProps={{
            step: 1,
            min: 1,
          }}
        />
        <ProFormDigit
          name={FakeParams.splitReadInterval}
          label={intl.formatMessage({id: 'pages.project.di.step.fake.splitReadInterval'})}
          tooltip={{
            title: intl.formatMessage({id: 'pages.project.di.step.fake.splitReadInterval.tooltip'}),
            icon: <InfoCircleOutlined/>,
          }}
          colProps={{span: 8}}
          initialValue={1}
          fieldProps={{
            step: 1,
            min: 1,
          }}
        />
        <ProFormDigit
          name={FakeParams.mapSize}
          label={intl.formatMessage({id: 'pages.project.di.step.fake.mapSize'})}
          colProps={{span: 8}}
          initialValue={5}
          fieldProps={{
            step: 1,
            min: 1,
          }}
        />
        <ProFormDigit
          name={FakeParams.arraySize}
          label={intl.formatMessage({id: 'pages.project.di.step.fake.arraySize'})}
          colProps={{span: 8}}
          initialValue={5}
          fieldProps={{
            step: 1,
            min: 1,
          }}
        />
        <ProFormDigit
          name={FakeParams.bytesLength}
          label={intl.formatMessage({id: 'pages.project.di.step.fake.bytesLength'})}
          colProps={{span: 8}}
          initialValue={5}
          fieldProps={{
            step: 1,
            min: 1,
          }}
        />
        <ProFormDigit
          name={FakeParams.stringLength}
          label={intl.formatMessage({id: 'pages.project.di.step.fake.stringLength'})}
          colProps={{span: 12}}
          initialValue={5}
          fieldProps={{
            step: 1,
            min: 1,
          }}
        />
        <ProFormSelect
          name={FakeParams.stringFakeMode}
          label={intl.formatMessage({id: 'pages.project.di.step.fake.stringFakeMode'})}
          colProps={{span: 12}}
          allowClear={false}
          request={() => {
            return DictDataService.listDictDataByType2(DICT_TYPE.seatunnelFakeMode)
          }}
        />
        <ProFormTextArea
          name={FakeParams.stringTemplate}
          label={intl.formatMessage({id: 'pages.project.di.step.fake.stringTemplate'})}
        />
        <ProFormSelect
          name={FakeParams.tinyintFakeMode}
          label={intl.formatMessage({id: 'pages.project.di.step.fake.tinyintFakeMode'})}
          colProps={{span: 8}}
          allowClear={false}
          request={() => {
            return DictDataService.listDictDataByType2(DICT_TYPE.seatunnelFakeMode)
          }}
        />
        <ProFormDigit
          name={FakeParams.tinyintMin}
          label={intl.formatMessage({id: 'pages.project.di.step.fake.tinyintMin'})}
          colProps={{span: 8}}
          initialValue={0}
          fieldProps={{
            min: 0,
            max: 127
          }}
        />
        <ProFormDigit
          name={FakeParams.tinyintMax}
          label={intl.formatMessage({id: 'pages.project.di.step.fake.tinyintMax'})}
          colProps={{span: 8}}
          initialValue={127}
          fieldProps={{
            min: 0,
            max: 127
          }}
        />
        <ProFormTextArea
          name={FakeParams.tinyintTemplate}
          label={intl.formatMessage({id: 'pages.project.di.step.fake.tinyintTemplate'})}
        />
        <ProFormSelect
          name={FakeParams.smallintFakeMode}
          label={intl.formatMessage({id: 'pages.project.di.step.fake.smallintFakeMode'})}
          colProps={{span: 8}}
          allowClear={false}
          request={() => {
            return DictDataService.listDictDataByType2(DICT_TYPE.seatunnelFakeMode)
          }}
        />
        <ProFormDigit
          name={FakeParams.smallintMin}
          label={intl.formatMessage({id: 'pages.project.di.step.fake.smallintMin'})}
          colProps={{span: 8}}
          initialValue={0}
          fieldProps={{
            min: 0,
            max: 32767
          }}
        />
        <ProFormDigit
          name={FakeParams.smallintMax}
          label={intl.formatMessage({id: 'pages.project.di.step.fake.smallintMax'})}
          colProps={{span: 8}}
          initialValue={32767}
          fieldProps={{
            min: 0,
            max: 32767
          }}
        />
        <ProFormTextArea
          name={FakeParams.smallintTemplate}
          label={intl.formatMessage({id: 'pages.project.di.step.fake.smallintTemplate'})}
        />
        <ProFormSelect
          name={FakeParams.intFakeMode}
          label={intl.formatMessage({id: 'pages.project.di.step.fake.intFakeMode'})}
          colProps={{span: 8}}
          allowClear={false}
          request={() => {
            return DictDataService.listDictDataByType2(DICT_TYPE.seatunnelFakeMode)
          }}
        />
        <ProFormDigit
          name={FakeParams.intMin}
          label={intl.formatMessage({id: 'pages.project.di.step.fake.intMin'})}
          colProps={{span: 8}}
          initialValue={0}
          fieldProps={{
            min: 0
          }}
        />
        <ProFormDigit
          name={FakeParams.intMax}
          label={intl.formatMessage({id: 'pages.project.di.step.fake.intMax'})}
          colProps={{span: 8}}
          fieldProps={{
            min: 0
          }}
        />
        <ProFormTextArea
          name={FakeParams.intTemplate}
          label={intl.formatMessage({id: 'pages.project.di.step.fake.intTemplate'})}
        />
        <ProFormSelect
          name={FakeParams.bigintFakeMode}
          label={intl.formatMessage({id: 'pages.project.di.step.fake.bigintFakeMode'})}
          colProps={{span: 8}}
          allowClear={false}
          request={() => {
            return DictDataService.listDictDataByType2(DICT_TYPE.seatunnelFakeMode)
          }}
        />
        <ProFormDigit
          name={FakeParams.bigintMin}
          label={intl.formatMessage({id: 'pages.project.di.step.fake.bigintMin'})}
          colProps={{span: 8}}
          initialValue={0}
          fieldProps={{
            min: 0
          }}
        />
        <ProFormDigit
          name={FakeParams.bigintMax}
          label={intl.formatMessage({id: 'pages.project.di.step.fake.bigintMax'})}
          colProps={{span: 8}}
          fieldProps={{
            min: 0
          }}
        />
        <ProFormTextArea
          name={FakeParams.bigintTemplate}
          label={intl.formatMessage({id: 'pages.project.di.step.fake.bigintTemplate'})}
        />
        <ProFormSelect
          name={FakeParams.floatFakeMode}
          label={intl.formatMessage({id: 'pages.project.di.step.fake.floatFakeMode'})}
          colProps={{span: 8}}
          allowClear={false}
          request={() => {
            return DictDataService.listDictDataByType2(DICT_TYPE.seatunnelFakeMode)
          }}
        />
        <ProFormDigit
          name={FakeParams.floatMin}
          label={intl.formatMessage({id: 'pages.project.di.step.fake.floatMin'})}
          colProps={{span: 8}}
          initialValue={0}
          fieldProps={{
            min: 0
          }}
        />
        <ProFormDigit
          name={FakeParams.floatMax}
          label={intl.formatMessage({id: 'pages.project.di.step.fake.floatMax'})}
          colProps={{span: 8}}
          fieldProps={{
            min: 0
          }}
        />
        <ProFormTextArea
          name={FakeParams.floatTemplate}
          label={intl.formatMessage({id: 'pages.project.di.step.fake.floatTemplate'})}
        />
        <ProFormSelect
          name={FakeParams.doubleFakeMode}
          label={intl.formatMessage({id: 'pages.project.di.step.fake.doubleFakeMode'})}
          colProps={{span: 8}}
          allowClear={false}
          request={() => {
            return DictDataService.listDictDataByType2(DICT_TYPE.seatunnelFakeMode)
          }}
        />
        <ProFormDigit
          name={FakeParams.doubleMin}
          label={intl.formatMessage({id: 'pages.project.di.step.fake.doubleMin'})}
          colProps={{span: 8}}
          initialValue={0}
          fieldProps={{
            min: 0
          }}
        />
        <ProFormDigit
          name={FakeParams.doubleMax}
          label={intl.formatMessage({id: 'pages.project.di.step.fake.doubleMax'})}
          colProps={{span: 8}}
          fieldProps={{
            min: 0
          }}
        />
        <ProFormTextArea
          name={FakeParams.doubleTemplate}
          label={intl.formatMessage({id: 'pages.project.di.step.fake.doubleTemplate'})}
        />

        <ProFormText
          name={FakeParams.dateYearTemplate}
          label={intl.formatMessage({id: 'pages.project.di.step.fake.dateYearTemplate'})}
          colProps={{span: 8}}
        />
        <ProFormText
          name={FakeParams.dateMonthTemplate}
          label={intl.formatMessage({id: 'pages.project.di.step.fake.dateMonthTemplate'})}
          colProps={{span: 8}}
        />
        <ProFormText
          name={FakeParams.dateDayTemplate}
          label={intl.formatMessage({id: 'pages.project.di.step.fake.dateDayTemplate'})}
          colProps={{span: 8}}
        />
        <ProFormText
          name={FakeParams.timeHourTemplate}
          label={intl.formatMessage({id: 'pages.project.di.step.fake.timeHourTemplate'})}
          colProps={{span: 8}}
        />
        <ProFormText
          name={FakeParams.timeMinuteTemplate}
          label={intl.formatMessage({id: 'pages.project.di.step.fake.timeMinuteTemplate'})}
          colProps={{span: 8}}
        />
        <ProFormText
          name={FakeParams.timeSecondTemplate}
          label={intl.formatMessage({id: 'pages.project.di.step.fake.timeSecondTemplate'})}
          colProps={{span: 8}}
        />

        <ProFormGroup
          label={intl.formatMessage({id: 'pages.project.di.step.schema'})}
          tooltip={{
            title: intl.formatMessage({id: 'pages.project.di.step.schema.tooltip'}),
            icon: <InfoCircleOutlined/>,
          }}
        >
          <ProFormList
            name={SchemaParams.fields}
            copyIconProps={false}
            creatorButtonProps={{
              creatorButtonText: intl.formatMessage({id: 'pages.project.di.step.schema.fields'}),
              type: 'text',
            }}
          >
            <ProFormGroup>
              <ProFormText
                name={SchemaParams.field}
                label={intl.formatMessage({id: 'pages.project.di.step.schema.fields.field'})}
                colProps={{span: 10, offset: 1}}
              />
              <ProFormText
                name={SchemaParams.type}
                label={intl.formatMessage({id: 'pages.project.di.step.schema.fields.type'})}
                colProps={{span: 10, offset: 1}}
              />
            </ProFormGroup>
          </ProFormList>
        </ProFormGroup>
      </DrawerForm>
    </XFlow>
  );
};

export default SourceFakeStepForm;

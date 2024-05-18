import React, {useEffect} from 'react';
import {Form} from 'antd';
import {DrawerForm, ProFormText,} from '@ant-design/pro-components';
import {getIntl, getLocale} from "@umijs/max";
import {Node, XFlow} from '@antv/xflow';
import {ModalFormProps} from '@/typings';
import {HDFSFileParams, STEP_ATTR_TYPE} from '../constant';
import {StepSchemaService} from '../helper';
import DataSourceItem from "../dataSource";
import FileSourceItem from "../common/file/fileSource";
import {InfoCircleOutlined} from "@ant-design/icons";

const SourceHdfsFileStepForm: React.FC<ModalFormProps<Node>> = ({data, visible, onVisibleChange, onOK}) => {
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
        <DataSourceItem dataSource={'HDFS'}/>
        <ProFormText
          name={HDFSFileParams.remoteUser}
          label={intl.formatMessage({id: 'pages.project.di.step.hdfsFile.remoteUser'})}
          tooltip={{
            title: intl.formatMessage({id: 'pages.project.di.step.hdfsFile.remoteUser.tooltip'}),
            icon: <InfoCircleOutlined/>,
          }}
        />
        <FileSourceItem/>
      </DrawerForm>
    </XFlow>
  );
};

export default SourceHdfsFileStepForm;